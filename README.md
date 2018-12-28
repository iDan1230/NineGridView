# NineGridView
新项目中需要做一个类似朋友圈的功能。造一个使用简单代码少的轮子<br>
![image](https://github.com/yangzhidan/NineGridView/blob/master/gif/1545988393462.gif)<br>
```
  <com.yzd.create.widgets.NineGridView
        android:id="@+id/nine"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        app:nineSpace="2dp"
        app:nineAlignment="true"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
```

## 两个自定义属性
```
<!--间距--><br>
<attr name="nineSpace" format="dimension"></attr><br>
<!--4张图时，是否已2行2列布局--><br>
<attr name="nineAlignment" format="boolean"></attr><br>
```
## 点击事件和设置数据
```
        nine.setOnNineListener(new NineGridView.OnNineClickListener() {
            @Override
            public void onNineListener(View v, int position, String url) {
              //todo
            }
        });
        nine.setUrls(urls);
```


