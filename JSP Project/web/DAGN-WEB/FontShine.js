/**
 * Created by 止水清潇 on 2017-02-17.
 * 实现功能：控制字体闪烁
 */
window.onload = function(){
    setInterval("colorChange()",1000);
}
var count = 0;
function colorChange() {
    var leftDiv = document.getElementById("leftDiv");
    var rightDiv = document.getElementById("rightDiv");
    var colorSets1 = new Array("#FFFFFF","#E0FFFF","#66FFFF","#996699","#00CC66","#666600","#FFCC33");
    var colorSets2 = new Array("#FFFFFF","#FFCC66","#99CCCC","#00FFFF","#000066","#663399","#993333");
    leftDiv.style.color = colorSets1[(count)%7];
    rightDiv.style.color = colorSets2[(count++)%7];
    count %= 7;
}