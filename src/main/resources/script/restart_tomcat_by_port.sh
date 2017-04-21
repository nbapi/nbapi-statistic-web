#!/bin/bash
# egg: sh restart_tomcat_by_port 8081
# func: find project depand on tomcat_8081, and restart the project     
tomcat_port=$1
if [ "$1" = "" ]; then
	echo "pelease input tomcat port"
	exit 1
fi
#获取profuctline 即tomcat的上层目录 比如/home/work/hotel/tomcat_8081的hotel目录
productline=`ps -ef | grep tomcat_$tomcat_port | grep catalina.sh | grep -v grep | awk {'print $9'} | awk -F '/' {'print $4'}`
if [ "$productline" = "" ]; then
	#获取失败，重试
	productline=`ps -ef | grep tomcat_$tomcat_port | grep "conf/logging.properties" | grep -v grep | awk {'print $9'} | awk -F '/' {'print $4'}`
fi
if [ "$productline" = "" ]; then
	#获取失败，通过find命令，查找tomcat路径(对/home/work路径进行全盘扫描，可能会比较慢)
	productline=`find /home/work/ -name tomcat_$tomcat_port -type d | awk -F '/' {'print $4'}`
fi

if [ "$productline" = "" ]; then
	echo "cannot find base dir"
        exit 1
fi


base_dir=/home/work/$productline
##base_file_list=`ls $base_dir`
#在/home/work/$productline下面查找stop_tomcat.sh（可能会有很多个）
base_file_list=`find $base_dir -name 'stop_tomcat.sh' | grep "script/stop_tomcat.sh"`
restart_status=0
for base_file in $base_file_list
do
	#stop_tomcat.sh路径中存在tomcat_字符串，不是真正需要的脚本
	if [[ "$base_file" =~ "tomcat_" ]]; then
		continue
	#stop_tomcat.sh路径中存在WEB-INF/classes字符串，不是真正需要的脚本
	elif [[ "$base_file" =~ "WEB-INF/classes" ]]; then 
		continue
	fi
	
	#如果是真正的start_tomcat.sh脚本，检查端口是否正确
	check_port=`cat $base_file | grep $tomcat_port`
	
	#端口为空，不是真正的stop脚本
	if [ "$check_port" = "" ]; then
		continue
	fi
	echo "停止tomcat_$tomcat_port"
        echo "开始执行:sh '$base_file"
        sh $base_file
        echo '执行完成' 
	
	base_file=`echo $base_file | sed 's/stop_tomcat.sh/start_tomcat.sh/g'`

        echo "启动tomcat_$tomcat_port"
        echo "开始执行:sh $base_file"
        sh $base_file
	echo '执行完成'
done
