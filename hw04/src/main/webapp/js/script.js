var ctx = function (method, url) {
    var xhr = new XMLHttpRequest();

    var setHtml = function (method, url, elemId, andThen) {

        xhr.open(method, url);
        xhr.onload =  function () {
            document.getElementById(elemId).innerHTML = xhr.response
        };
        xhr.onerror = function () {
            console.log('status:' + this.status + ', statusText:' +  xhr.statusText)
        };
        xhr.send();
    }

    return {
        updateCurrencies : function(){
            setHtml("get", "/currencyratings", "currencies");
        },
        updateNews : function(){
            setHtml("get", "/news", "news");
        }
    }
}();

//работает и при выключенном http 2.0
var update = function () {
    ctx.updateCurrencies();
    setTimeout("ctx.updateNews()", 500);
};
update();
setInterval('update()', 20000);
