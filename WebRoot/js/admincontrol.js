$(function () {
    getLeftMenu();//获取左侧菜单并加载
    setInterval("getCurrentTime()", "1000");//获得系统时间
});

//获得系统时间
function getCurrentTime() {
    $("#info").text(new Date().toLocaleString() + '  星期' + '日一二三四五六'.charAt(new Date().getDay()));
}


function showsubmenu1() {

    var whichEl = document.getElementById("submenu1");
    var menuTitle = document.getElementById("menuTitle1");
    if (whichEl != null) {
        if (whichEl.style.display == "none") {
            whichEl.style.display = '';
            if (menuTitle != null)
                menuTitle.className = 'menu_title';
        } else {
            whichEl.style.display = 'none';
            if (menuTitle != null)
                menuTitle.className = 'menu_title2';
        }
    }
}


