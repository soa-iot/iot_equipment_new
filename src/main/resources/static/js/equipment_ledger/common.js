/**
 * 获取guid
 * 
 * @return {}
 */
function guid() {
	return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
				var r = Math.random() * 16 | 0, v = c == 'x'
						? r
						: (r & 0x3 | 0x8);
				return v.toString(16);
			});
}
/**
 * 获取url中的参数
 */
function getParams(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
};

/**
 * 获取指定cookie
 * 
 * @param {}
 *            cookieName
 * @return {String}
 */
function getCookie(cookieName) {
	var strCookie = document.cookie;
	var arrCookie = strCookie.split("; ");
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (cookieName == arr[0]) {
			return arr[1];
		}
	}
	return "";
}



