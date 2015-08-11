<%@page import="sepim.server.clients.World"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>    
    <title>My JSP 'main.jsp' starting page</title>
  </head>
<script type="text/javascript"
src="http://webapi.amap.com/maps?v=1.3&key=yourkey">
</script>
<script type="text/javascript">
var mapObj;
var marker;  
var marker2;  
var marker3;  
var cluster;  
var markers = []; 
var lineArr;
var cloudDataLayer;
//初始化地图对象，加载地图  
function mapInit(){  
    mapObj = new AMap.Map("container",{  
    center:new AMap.LngLat(116.397428,39.90923), //地图中心点  
    level:11  //地图显示的比例尺级别  
    //zoomEnable:false
    }); 
    AMap.event.addListener(mapObj,'click',getLnglat); //点击事件    
}  
//鼠标点击，获取经纬度坐标  
function getLnglat(e){    
    var x = e.lnglat.getLng();
    var y = e.lnglat.getLat(); 
    document.getElementById("lnglat").innerHTML = x + "," + y;
}
//清空地图
function clearMap(){
    mapObj.clearMap();
    cloudDataLayer.setMap(null);
}
function addMarker2(){
    mapObj.clearMap();
    marker2 = new AMap.Marker({  
        position:(new AMap.LngLat(116.384182,39.916451)),  
        draggable:true,  //点标记可拖拽  
        cursor:'move',  
        raiseOnDrag:true //鼠标拖拽点标记时开启点标记离开地图的效果
    });  
    marker2.setMap(mapObj);  
}
function marker2Cartoon(){
    marker2.setAnimation('AMAP_ANIMATION_BOUNCE'); //设置点标记的动画效果，此处为弹跳效果
}
function iJuhe(){
    mapObj.clearMap();
    // 随机向地图添加500个标注点  
    var mapBounds = mapObj.getBounds();  
    var sw = mapBounds.getSouthWest();  
    var ne = mapBounds.getNorthEast();  
    var lngSpan = Math.abs(sw.lng - ne.lng);  
    var latSpan = Math.abs(ne.lat - sw.lat);  
      
    for (var i = 0; i < 500; i ++) {  
        var markerPosition = new AMap.LngLat(sw.lng + lngSpan * (Math.random() * 1),ne.lat - latSpan * (Math.random() * 1));  
        var marker = new AMap.Marker({  
            map:mapObj,  
            position:markerPosition, //基点位置  
            icon:"js_marker.png", //marker图标，直接传递地址url  
            offset:{x:-8,y:-34} //相对于基点的位置  
        });  
        markers.push(marker);  
    }  
    setTimeout(function(){
        addCluster(0); e
    },500);     
}
function addCluster(tag)  
{  
    if(cluster) {     
        cluster.setMap(null);  
    }  
    if(tag==1) {  
        var sts=[{url:"js_1.png",size:new AMap.Size(32,32),offset:new AMap.Pixel(-16,-30)},  
                   {url:"js_2.png",size:new AMap.Size(32,32),offset:new AMap.Pixel(-16,-30)},  
                   {url:"js_3.png",size:new AMap.Size(48,48),offset:new AMap.Pixel(-24,-45), textColor:'#CC0066'}];  
        mapObj.plugin(["AMap.MarkerClusterer"],function(){  
            cluster = new AMap.MarkerClusterer(mapObj,markers,{styles:sts});  
        });  
    }  
    else {  
        mapObj.plugin(["AMap.MarkerClusterer"],function(){  
            cluster = new AMap.MarkerClusterer(mapObj,markers);  
        });  
    }  
}
//地图图块加载完毕后执行函数  
function completeEventHandler(){    
    marker3 = new AMap.Marker({  
        map:mapObj,  
        //draggable:true, //是否可拖动  
        position:new AMap.LngLat(116.273881,39.807409),//基点位置  
        icon:"http://code.mapabc.com/images/car_03.png", //marker图标，直接传递地址url  
        offset:new AMap.Pixel(-26,-13), //相对于基点的位置  
        autoRotation:true  
    });        
    var lngX = 116.273881;  
    var latY = 39.807409;         
    lineArr = new Array();   
    lineArr.push(new AMap.LngLat(lngX,latY));   
    for (var i = 1; i <30; i++){   
        lngX = lngX+Math.random()*0.05;   
        if(i%2){   
            latY = latY+Math.random()*0.0001;   
        }else{   
            latY = latY+Math.random()*0.06;   
        }   
        lineArr.push(new AMap.LngLat(lngX,latY));   
    }  
    //绘制轨迹  
    var polyline = new AMap.Polyline({  
        map:mapObj,  
        path:lineArr,  
        strokeColor:"#00FF00",//线颜色  
        strokeOpacity:1,//线透明度  
        strokeWeight:3,//线宽  
        strokeStyle:"solid",//线样式  
    });  
}  
function startRun(){  //开始播放动画
    completeEventHandler();
    marker3.moveAlong(lineArr,80);     //开始轨迹回放
}
function endRun(){   //结束动画播放
    marker3.stopMove();  //暂停轨迹回放
}
//添加多边形覆盖物  
function addPolygon(){  
   var polygonArr=new Array();//多边形覆盖物节点坐标数组   
   polygonArr.push(new AMap.LngLat("116.319809","39.956596"));   
   polygonArr.push(new AMap.LngLat("116.556702","39.983434"));   
   polygonArr.push(new AMap.LngLat("116.483917","39.845449"));   
   polygonArr.push(new AMap.LngLat("116.244278","39.848612"));   
   polygon=new AMap.Polygon({     
   path:polygonArr,//设置多边形边界路径  
   strokeColor:"#0000ff", //线颜色  
   strokeOpacity:0.2, //线透明度   
   strokeWeight:3,    //线宽   
   fillColor: "#f5deb3", //填充色  
   fillOpacity: 0.35//填充透明度  
  });   
   polygon.setMap(mapObj);  
 }  

//在指定位置打开默认信息窗体  
function openInfo(){    
    //构建信息窗体中显示的内容  
    var info = [];   
    info.push("<div><div><img style=\"float:left;\" src=\" http://webapi.amap.com/images/autonavi.png \"/></div> ");   
    info.push("<div style=\"padding:0px 0px 0px 4px;\"><b>高德软件</b>");    
    info.push("电话 : 010-84107000   邮编 : 100102");    
    info.push("地址 : 北京市望京阜通东大街方恒国际中心A座16层</div></div>");            
    inforWindow = new AMap.InfoWindow({    
        content:info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容  
    });   
    inforWindow.open(mapObj,new AMap.LngLat(116.373881,39.907409));  
}  

//实例化信息窗体  
var infoWindow2 = new AMap.InfoWindow({  
    isCustom:true,  //使用自定义窗体  
    content:createInfoWindow('方恒假日酒店&nbsp;&nbsp;<span style="font-size:11px;color:#F00;">价格:318</span>',"<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134' style='float:left;margin:0 5px 5px 0;'>地址：北京市朝阳区阜通东大街6号院3号楼 东北 8.3 公里<br/>电话：010 64733333<br/><a href='http://baike.baidu.com/view/6748574.htm'>详细信息</a>"),  
    size:new AMap.Size(300, 0),  
    offset:new AMap.Pixel(0, -50)//-113, -140  
}); 

//关闭信息窗体  
function closeInfoWindow(){  
    mapObj.clearInfoWindow();  
} 
//构建自定义信息窗体   
function createInfoWindow(title,content){  
    var info = document.createElement("div");  
    info.className = "info";  
    // 定义顶部标题  
    var top = document.createElement("div");  
    top.className = "info-top";  
      var titleD = document.createElement("div");  
      titleD.innerHTML = title;  
      var closeX = document.createElement("img");  
      closeX.src = "close2.gif";  
      closeX.onclick = closeInfoWindow;  
        
    top.appendChild(titleD);  
    top.appendChild(closeX);  
    info.appendChild(top);  
    // 定义中部内容  
    var middle = document.createElement("div");  
    middle.className = "info-middle";  
    middle.innerHTML = content;  
    info.appendChild(middle);  
      
    // 定义底部内容  
    var bottom = document.createElement("div");  
    bottom.className = "info-bottom";  
    var sharp = document.createElement("img");  
    sharp.src = "sharp.png";  
    bottom.appendChild(sharp);    
    info.appendChild(bottom);  
    return info;  
} 
function myWindow(){
    infoWindow2.open(mapObj,new AMap.LngLat(116.373881,39.907409));
}
function xmMap(){
    mapObj.setZoom(15);
    var bounds = new AMap.Bounds(new AMap.LngLat(118.055005,24.435624), new AMap.LngLat(118.078351,24.454299)),  
    groundImageOpts = {  
        opacity: 1,   //图片透明度  
        clickable: true,//图片相应鼠标点击事件，默认：false  
        map: mapObj     //图片叠加的地图对象  
    }; 
    //实例化一个图片覆盖物对象  
    var groundImage = new AMap.GroundImage('gly.png', bounds, groundImageOpts);      
    setTimeout(function(){
        mapObj.setCenter(new AMap.LngLat(118.067665,24.443946));
    },500);
    //mapObj.setCenter(new AMap.LngLat(118.067665,24.443946));
}
</script>
  <body onload="mapInit()">
    <jsp:include page="menu.jsp"/>
    <div id="container" style="width:1100;height:500"></div>
    <div id="iControlbox">
        <p>坐标：<span id="lnglat">&nbsp;</span></p>
        <ul>
            <li><button onclick="javascript:addMarker2();">位置显示</button><button onclick="javascript:clearMap();">清空地图</button></li>
            <li><button onclick="javascript:startRun();">历史轨迹</button><button onclick="javascript:endRun();">停止</button><button onclick="javascript:clearMap();">清空地图</button></li>
            <li><button onclick="javascript:addPolygon();">电子围栏</button><button onclick="javascript:clearMap();">清空地图</button></li>
        </ul>
    </div>
  </body>
  <jsp:include page="footer.jsp"/>
</html>
