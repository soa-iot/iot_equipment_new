/*
 * 引入其他插件
 */
//document.write('<script type="text/javascript" src="/package/layui-v2.4.2/layui.all.js"></script>');
//引入layui插件
/**
    一、js目录
		1.layui验证扩展方法
		2.生成html方法
		3.js获取当前时间
		4.js获取当前日期
 */

/**
 *layui表单验证
 
layui.form.verify({
	tagValue: function(value, item){
		if(!/^(0|[1-9]+[0-9]*)(px|%)?$/.test(value)){
			return '输入数据格式不对';
		}
	}
})
*/

/**
 * js生成html(重写)
 * wholePageHtml--输出全部的html代码
 */
var generateHTML = function(wholePageHtml, pageName){
	var urlObject = window.URL || window.webkitURL || window; 
    var export_blob = new Blob([wholePageHtml]);
    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = pageName;
	var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent(
        "click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null
    );
    save_link.dispatchEvent(ev);
}

/**
 * 获取当前时间
 * seperator1日期分割符号
 * seperator2时间分割符号
 */
var getNowTime = function( seperator1 , seperator2 ){ 
	var nowDate = new Date();
	return  nowDate.getFullYear() + seperator1 + ( nowDate.getMonth() + 1 ) +
			seperator1 + nowDate.getDate() +
			" " + 
		    nowDate.getHours() + seperator2 + nowDate.getMinutes() + seperator2 + 
		    nowDate.getSeconds();
}

/**
 * 获取当前日期
 * seperator日期分割符号
 */
var getFullNowDate = function getNowDate(seperator){
	var nowDate = new Date();
	return  nowDate.getFullYear() + seperator + 
			(nowDate.getMonth() + 1) + seperator + nowDate.getDate();
}

/**
 * 获取当前日期的前一个月
 * seperator日期分割符号
 */
var getFullLastMonthDate = function(seperator){
	var nowDate = new Date();
	return  nowDate.getFullYear() + seperator + 
			nowDate.getMonth() + seperator + nowDate.getDate();
}

/**
 * 获取当前日期，标准的格式（YYYY-MM-DD）
 */
var getFullNowDate1 = function getNowDate(){
	var nowDate = new Date()
	,month = (nowDate.getMonth() + 1) > 9 ? nowDate.getMonth() : "0" + (nowDate.getMonth())
	,now = (nowDate.getDate()) > 9 ? nowDate.getDate() : "0" + (nowDate.getDate());
	return  nowDate.getFullYear() + "-" + 
			month + "-" + now;
}


/**
 * 判断当前月份有无31号(传入日期间隔符为-)
 * 有返回对应的下标，没有返回-1
 */
var judgeMonthHas31 = function( currDate ){
	var base = new Array("1","3","5","7","8","10","12")
	,month = currDate.split("-")[1];
	$.trim(month);
	return $.inArray(month, base);
}




/*-----------------------------------
      url相关方法
-----------------------------------*/
/**
 * 根据浏览器url的参数名，获取参数值
 */
function getUrlParamValueByName ( name ) {
    var reg = new RegExp( "(^|&)" + name + "=([^&]*)(&|$)", "i" );
    var r = window.location.search.substr( 1 ).match( reg ); //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if ( r != null )
    context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}

/**
 * 根据浏览器urlf的参数名，获取中文参数值
 */
function getQueryUrlString( name ) {
    var reg = new RegExp( '(^|&)' + name + '=([^&]*)(&|$)', 'i' );
    var r = window.location.search.substr( 1 ).match( reg );
    if( r != null ) {
        return decodeURI( r[2] );
    }
    return "请选择";
}

/*-------------------------------------------------------------------------
                              cookie相关方法
--------------------------------------------------------------------------*/
/**
 * 获得cookie 的值
 */
function getCookie1( name ){    
   var cookieArray=document.cookie.split( "; " ); 
   for ( var i=0; i<cookieArray.length; i++ ){    
      var arr = cookieArray[i].split( "=" );       //将名和值分开    
      if( arr[0] == name ) return decodeURI( arr[1] ); //如果是指定的cookie，则返回它的值    
   } 
   return ""; 

} 

function getCookie( objName ){//获取指定名称的cookie的值
    var arrStr = document.cookie.split( "; " );
    for( var i = 0;i < arrStr.length; i++ ){
        var temp = arrStr[i].split("=");
        if( temp[0] == objName ) return unescape( temp[1] );
    } 
}
/*
function getCookie( name ){
    var arr = document.cookie.match( new RegExp( "(^| )"+name+"=([^;]*)(;|$)" ) );
	if(arr != null) return unescape( arr[2] ); 
	return null;
}
*/
 
/*
 * 添加cookie
 */
function addCookie( objName, objValue, objHours){      
    var str = objName + "=" + escape( objValue );
    if( objHours > 0 ){                               //为时不设定过期时间，浏览器关闭时cookie自动消失
        var date = new Date();
        var ms = objHours*3600*1000;
        date.setTime( date.getTime() + ms);
        str += "; expires=" + date.toGMTString();
   }
   document.cookie = str;
}
 
/*
 * 两个参数，一个是cookie的名子，一个是值
 * name cookie名
 * name cookie值
 * days 存储的天数
 */
function addCookie1( name, value, days ){
    var exp = new Date();   
    exp.setTime( exp.getTime() + days*24*60*60*1000 );
    document.cookie = 
    	name + "=" + escape (value) + ";expires=" + exp.toGMTString() + "; path=/";
}

/*
 * 删除cookie  
 */  
function delCookie( name ){
    var exp = new Date();
    exp.setTime( exp.getTime() - 1 );
    var cval = getCookie( name );
    if( cval!=null ) {
    	document.cookie= name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}

function delCookie1( name ){
	setCookie(name, "", -1);
}

function clearAllCookie2() {

	var domain = '.'+location.host;
	var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
	if( keys ) {
		for( var i = keys.length; i--;)
			document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()+"; Domain="+domain+"; path=/";
	}
}


/**
 * 获取json对象的长度
 */
var getJsonLength = function( jsonData ){
	var length = 0;
	for( var i in jsonData ){
		length++;
	}
	return length;
}

/**
 * UUID生成
 * @param {} len
 * @param {} radix
 * @return {}
 */
Math.uuid = function (len, radix) {
	var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;

    if (len) {
		// Compact form
		for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
		// rfc4122, version 4 form
		var r;

		// rfc4122 requires these characters
      	uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      	uuid[14] = '4';

      	// Fill in random data.  At i==19 set the high bits of clock sequence as
      	// per rfc4122, sec. 4.1.5
     	for (i = 0; i < 36; i++) {
        	if (!uuid[i]) {
          		r = 0 | Math.random()*16;
          		uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
    }

	return uuid.join('');
};

/**
 * get方式的ajax请求(同步)-js原生没有重载和重写
 * @type 
 */
var ajaxByGet = function(url, data, successGetMethod, requestType){
	 $.ajax({
	     type: "GET",
	     url: url,
	     data: data,
	     async: requestType && true, //默认
	     cache: true, //默认
	     contentType: "application/x-www-form-urlencoded",//默认
	     dataType: "json",//必须指定，否则根据后端判断
	     beforeSend:  function(XMLHttpRequest){//在beforeSend中如果返回false可以取消本次ajax请求
	         //this;   //调用本次ajax请求时传递的options参数
	     },
	     complete:   function(XMLHttpRequest, textStatus){//请求完成后调用的回调函数（请求成功或失败时均调用）
	        //this;    //调用本次ajax请求时传递的options参数
	     },
	     success: successGetMethod,
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}






/*-------------------------------------------------------------------------
                              ajax 相关方法
--------------------------------------------------------------------------*/
/**
 * ajax静态代理proxy
 * @param {} url
 * @param {} data
 * @param {} successPostMethod
 * @param {} requestType
 */
function ajax( t, u, d, f, a ){
	console.log( 'ajax代理proxy……' );
	switch( arguments.length ){
		case 4:
			switch( t.toUpperCase() ){
				case 'GET':
					gAjax( u, d, f );
					break;
				case 'POST':
					pAjax( u, d, f );
					break;
				case 'PUT':
					puAjax( u, d, f );
					break;
				case 'DELETE':
					dAjax( u, d, f );
					break;
			}
			break;
		case 5:
			switch( t.toUpperCase() ){
			case 'GET':
				gAjax1( u, d, f, a );
				break;
			case 'POST':
				pAjax1( u, d, f, a );
				break;
			case 'PUT':
				puAjax1( u, d, f, a );
				break;
			case 'DELETE':
				dAjax1( u, d, f, a );
				break;
			}
			break;
	}
}


/**
 * ajax-get同步访问模板
 */
function gAjax( url, data, succFuncName ){
	console.log( 'ajax-gAjax同步访问模板……' );
	$.ajax({
	     type : "GET",
	     url : url,
	     data : data,
	     async : false, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				layer.msg( jsonData.message, {icon:1} );
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-get异步访问模板
 */
function gAjax1( url, data, succFuncName, requestType){
	console.log( 'ajax-gAjax1异步访问模板……' );
	$.ajax({
	     type : "GET",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				succFuncName(data);
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-post异步访问模板
 */
function pAjax( url, data, succFuncName ){
	console.log( 'ajax-pAjax访问模板……' );
	$.ajax({
	     type : "POST",
	     url : url,
	     data : data,
	     async : true, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				succFuncName( data );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-post同步访问模板
 */
function pAjax1( url, data, succFuncName, requestType ){
	console.log( 'ajax-pAjax1访问模板……' );
	$.ajax({
	     type : "POST",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				succFuncName( data );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-put异步访问模板
 */
function puAjax( url, data, succFuncName ){
	console.log( 'ajax-puAjax访问模板……' );
	$.ajax({
	     type : "PUT",
	     url : url,
	     data : data,
	     async : true, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				succFuncName(data);
	 				//eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败');
	     }		       
	});
}

/**
 * ajax-put同步访问模板
 */
function puAjax1( url, data, succFuncName, requestType ){
	console.log( 'ajax-puAjax1访问模板……' );
	$.ajax({
	     type : "PUT",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				//eval( succFuncName + "(data)" );
	 				succFuncName( data );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-delete异步访问模板
 */
function dAjax( url, data, succFuncName ){
	console.log( 'ajax-dAjax异步访问模板……' );
	$.ajax({
	     type : "DELETE",
	     url : url,
	     data : data,
	     async : true, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				succFuncName(data);
	 				//eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败');
	     }		       
	});
}

/**
 * ajax-delete同步访问模板
 */
function dAjax1( url, data, succFuncName, requestType ){
	console.log( 'ajax-dAjax1同步访问模板……' );
	$.ajax({
	     type : "DELETE",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/x-www-form-urlencoded",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				//eval( succFuncName + "(data)" );
	 				succFuncName( data );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}



/**
 * contentType为json串
 * @param {} t
 * @param {} u
 * @param {} d
 * @param {} f
 * @param {} a
 */
function ajaxByJson( t, u, d, f, a ){
	console.log( 'ajax代理proxy……' );
	switch( arguments.length ){
		case 4:
			switch( t.toUpperCase() ){
				case 'GET':
					gAjaxByJson( u, d, f );
					break;
				case 'POST':
					pAjaxByJson( u, d, f );
					break;
				case 'PUT':
					puAjaxByJson( u, d, f );
					break;
				case 'DELETE':
					break;
			}
			break;
		case 5:
			switch( t.toUpperCase() ){
			case 'GET':
				gAjax1ByJson( u, d, f, a );
				break;
			case 'POST':
				pAjax1ByJson( u, d, f, a );
				break;
			case 'PUT':
				puAjax1ByJson( u, d, f, a );
				break;
			case 'DELETE':
				break;
			}
			break;
	}
}


/**
 * ajax-get同步访问模板
 */
function gAjaxByJson( url, data, succFuncName ){
	console.log( 'ajax-gAjax同步访问模板……' );
	$.ajax({
	     type : "GET",
	     url : url,
	     data : data,
	     async : false, 
	     cache : true,
	     contentType : "application/json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				layer.msg( jsonData.message, {icon:1} );
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-get异步访问模板
 */
function gAjax1ByJson( url, data, succFuncName, requestType){
	console.log( 'ajax-gAjax1异步访问模板……' );
	$.ajax({
	     type : "GET",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-post异步访问模板
 */
function pAjaxByJson( url, data, succFuncName ){
	console.log( 'ajax-pAjax访问模板……' );
	$.ajax({
	     type : "POST",
	     url : url,
	     data : data,
	     async : true, 
	     cache : true,
	     contentType : "application/json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				console.log( '111111111111' );
	 				succFuncName( data );
	 				console.log( '222222222222' );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-post同步访问模板
 */
function pAjax1ByJson( url, data, succFuncName, requestType ){
	console.log( 'ajax-pAjax1访问模板……' );
	$.ajax({
	     type : "POST",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-put异步访问模板
 */
function puAjaxByJson( url, data, succFuncName ){
	console.log( 'ajax-puAjax访问模板……' );
	$.ajax({
	     type : "PUT",
	     url : url,
	     data : data,
	     async : true, 
	     cache : true,
	     contentType : "application/json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * ajax-put同步访问模板
 */
function puAjax1ByJson( url, data, succFuncName, requestType ){
	console.log( 'ajax-puAjax1访问模板……' );
	$.ajax({
	     type : "PUT",
	     url : url,
	     data : data,
	     async : requestType || false, 
	     cache : true,
	     contentType : "application/json",
	     dataType : "json",
	     success : function( jsonData ){
	 		if( jsonData ){
	 			var data = jsonData.data;
	 			if( jsonData.state == 0 && data ){
	 				eval( succFuncName + "(data)" );
	 			}else{
	 				layer.msg( jsonData.message, {icon:2} );
	 			}		
	 		}else{
	 			layer.msg( '请求失败', {icon:2} );
	 		}		
	     },
	     error:function(){
	    	 layer.msg('请求失败');
	     }		       
	});
}


/**
 * post方式的ajax请求(同步)-已过时
 * @type 
 */
var ajaxByPost = function(url, data, successPostMethod, requestType){
	 $.ajax({
	     type: "POST",
	     url: url,
	     data: data,
	     async: requestType && true, //默认
	     cache: true, //默认
	     contentType: "application/x-www-form-urlencoded",//默认
	     dataType: "json",//必须指定，否则根据后端判断
	     beforeSend:  function(XMLHttpRequest){//在beforeSend中如果返回false可以取消本次ajax请求
	         //this;   //调用本次ajax请求时传递的options参数
	     },
	     complete:   function(XMLHttpRequest, textStatus){//请求完成后调用的回调函数（请求成功或失败时均调用）
	        //this;    //调用本次ajax请求时传递的options参数
	     },
	     success: successPostMethod,
	     error:function(){
	    	 layer.msg('请求失败：');
	     }		       
	});
}

/**
 * put方式的ajax请求
 */
var ajaxByPut = function(url, data, successPutMethod ){
	data['_method'] = 'put';
	$.ajax({
         type: "post",
         url: url,
         data: data,
         async: false, //默认
         cache: true, //默认
         contentType: "application/x-www-form-urlencoded",//默认
         dataType: "json",//必须指定，否则根据后端判断
         beforeSend:  function(XMLHttpRequest){//在beforeSend中如果返回false可以取消本次ajax请求
             //this;   //调用本次ajax请求时传递的options参数
         },
         complete:   function(XMLHttpRequest, textStatus){//请求完成后调用的回调函数（请求成功或失败时均调用）
            //this;    //调用本次ajax请求时传递的options参数
         },
         success: successPutMethod,	
         error:function(){
        	 layer.msg('请求失败：未成功连接，请检查网络');
         }		       
     });
}

/**
 *delete方式的ajax请求
 */
var ajaxByDelete = function(url, data, successDeleteMethod ){
	data['_method'] = 'delete';
	$.ajax({
         type: "post",
         url: url,
         data: data,
         async: false, //默认
         cache: true, //默认
         contentType: "application/x-www-form-urlencoded",//默认
         dataType: "json",//必须指定，否则根据后端判断
         beforeSend:  function(XMLHttpRequest){//在beforeSend中如果返回false可以取消本次ajax请求
             //this;   //调用本次ajax请求时传递的options参数
         },
         complete:   function(XMLHttpRequest, textStatus){//请求完成后调用的回调函数（请求成功或失败时均调用）
            //this;    //调用本次ajax请求时传递的options参数
         },
         success: successDeleteMethod,	
         error:function(){
        	 layer.msg('请求失败：未成功连接，请检查网络');
         }		       
     });
}

/**
 *查询字符串是否包含特定字符
 */
var contains = function(strAll, strPart){
	if(strAll.indexOf(strPart) > 0){
		return true;
	}
	return false;
}

/**
 * 停止事件冒泡
 * @param {} e
 */
function stopPropagation(e) { 
	if (e.stopPropagation){
		e.stopPropagation(); 
	}else{
		e.cancelBubble = true;
	}
} 

/*-----------------------------------
	数组相关方法
-----------------------------------*/

/**
 * 查询数组中指定值的位置
 * @param {} value
 * @return {}
 */
Array.prototype.indexOf = function(value){
	for (var i = 0; i < this.length; i++) {
		if (this[i] == value) {
			return i;
		}
	}
	return -1;
}

/**
 * 删除数组中的指定元素
 * @param {} value
 */
Array.prototype.remove = function(value){
	var index = this.indexOf(value);
	if (index > -1) {
		this.splice(index, 1);
	}
}

/**
 * 数组去重
 * @param {} value
 */


/**
 * 数组包含元素
 * @param {} value
 */



/*-----------------------------------
		Json相关方法
-----------------------------------*/

/**
* form.serilize()字符串转json
* @param {} value
* @return {}
*/
function formToJson( data ) {
   data = data.replace( /&/g,"\",\"" );
   data = data.replace( /=/g,"\":\"" );
   data = "{\""+data+"\"}";
   return data;
}



/*-----------------------------------
		浏览器相关方法
-----------------------------------*/
/**
 * 获取浏览器中url中的参数
 */
(function ($) {
    $.getUrlParam = function ( name ) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);





/*-----------------------------------
           
-----------------------------------*/
/**
 * 前端打印方法
 */
var p = function( info ){
	console.log( info );
}



/*-----------------------------------
                              前端生成Excel
-----------------------------------*/
/**
* 前端导出报表
*/
function exportExcel( htmlId, name, buttonId ){
console.log('导出报表按钮……');
// 使用outerHTML属性获取整个table元素的HTML代码（包括<table>标签），然后包装成一个完整的HTML文档，
// 设置charset为urf-8以防止中文乱码
var html = "<html><head><meta charset='utf-8' /></head><body>" 
+ $( "#" + htmlId ).html() + "</body></html>";
// 实例化一个Blob对象，其构造函数的第一个参数是包含文件内容的数组，第二个参数是包含文件类型属性的对象
var blob = new Blob( [html], { type: "application/vnd.ms-excel" });
$( 'body' ).append('<a id="aExport" style="display:none"></a>');
var a = $( '#aExport' )[0];
// 利用URL.createObjectURL()方法为a元素生成blob URL
a.href = URL.createObjectURL(blob);
// 设置文件名
a.download = name + ".xls";
document.getElementById(buttonId).click();
//$( '#aExport' ).click();
return false;
}