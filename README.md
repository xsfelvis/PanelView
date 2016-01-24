# PanelView
仪表盘的自定义view<br>
![](http://img.blog.csdn.net/20160124183250313?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  
![](http://img.blog.csdn.net/20160124183309204?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  

#How to use

add a PanelView.PanelView into your xml <br>
```
<PanelView.PanelView
        android:id="@+id/panView"
        panel:Unit=" Km/h"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="当前速度"/>
```
        
more attrs to see values/attr,you can choose attr you need<br>

you can also change attr by calling method like,more details to see PanelView.java
```
setPercent(int percent);
setText(String text);
setTextSize(int size);
setArcColor(int color);
setPointerColor(int color);
setArcWidth(int width);
setPointerColor(int color);
setAcrStartColor(int acrStartColor);
setAcrEndColor(int acrEndColor);<br>
setUnit(String unit);<br>
```


#More to see my csdn blog
[我的csdn博客](http://blog.csdn.net/xsf50717/article/details/50574160) 
