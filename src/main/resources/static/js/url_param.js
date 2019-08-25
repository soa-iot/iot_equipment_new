/**
 * 地址解析
 * @returns
 */

function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  $.trim(decodeURI(r[2])); return null;
}

/**
 * 获取地址和端口
 * @returns ip：端口号
 */
function getUrlIp(){
	
	return $.trim(window.location.href.split("/")[2]);
}
