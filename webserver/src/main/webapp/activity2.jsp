<%@ page import="main.java.model.CityRateData" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: swz
  Date: 2017/9/11
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/activity.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
    <script src="echarts.js"></script>
</head>

<body>
<div id="background" >
<header>
    <nav>
        <ul>
            <li class="logo"><a href="index.jsp">Home page</a></li>

            <li><a href="#menu" id="menu" onclick="showMenu()">Menu</a></li>
            <li><a href="">Contact</a></li>
            <li><a href="">Top</a></li>
        </ul>
    </nav>
</header>

<div id="newMenu">
    <div>
        <h2>Menu</h2>
        <hr>
        <ul>
            <li><a href="activity.jsp">Activity analysis</a></li>
            <li><a href="phone.jsp">Phone analysis</a></li>
            <li><a href="timeline.jsp">Timeline analysis</a></li>
            <li><a href="personas.jsp">Personas analysis</a></li>
            <li><a href="website.jsp">Hot20 websites</a></li>
        </ul>
    </div>
</div>
</header>

<div>
    <div class="activity-info">

    </div>
</div>


    <%
        ArrayList list=(ArrayList)request.getAttribute("crdlist");

        System.out.println(list.size());
        String []cityname=new String[300];
        int []pv=new int[100];
        int []uv=new int[100];
        double []ana=new double[100];
        for(int i=0;i<list.size();i++){
            CityRateData city=(CityRateData)list.get(i);
            cityname[i]=city.getName();
            pv[i]=city.getPV();
            uv[i]=city.getUV();
            ana[i]=city.getRate();
        }
    %>
    <div style="width:100%;height:1050px;margin:0;opacity:.5;">

        <div style="width:100%;position:relative;top:60px;">
            <div id="container" style="width: 100%;height:900px;"></div>
            <a href="activity.jsp" style="display:block;margin:15px auto;
          width:150px;line-height:50px;background:#000;color:#FFF;
          text-decoration:none;text-align:center;border-radius:15px;
          font-size:28px;">Back</a>
        </div>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
        <script type="text/javascript">
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        var geoCoordMap = {
            "北京市":[116.4007503787,39.9031844643
            ],"广州市":[113.2596665241,23.1317626641
            ],"上海市":[121.469458249,31.2319316784
            ],"天津市":[117.196712841,39.0867296952
            ],"重庆市":[106.5482798308,29.565526745
            ],"辽宁省沈阳市":[123.4539661859,41.6801384769
            ],"江苏省南京市":[118.7907996424,32.0603627958
            ],"湖北省武汉市":[114.2995528195,30.5952058577
            ],"四川省成都市":[104.0645225402,30.573883935
            ],"陕西省西安市":[108.9353562803,34.3425803471
            ],"河北省邯郸市":[114.5332773418,36.6255057093
            ],"河北省石家庄市":[114.5089972984,38.0417774681
            ],"河北省保定市":[115.4586564667,38.873009118
            ],"河北省张家口市":[114.8798780167,40.7662123244
            ],"河北省承德市":[117.9566704804,40.9499365609
            ],"河北省唐山市":[118.1737451625,39.6289958309
            ],"河北省廊坊市":[116.6780759005,39.5365247894
            ],"河北省沧州市":[116.8332465167,38.3037229637
            ],"河北省衡水市":[115.663122622,37.7382041118
            ],"河北省邢台市":[114.4990644252,37.070118515
            ],"河北省秦皇岛市":[119.5962475641,39.934718397
            ],"山西省朔州市":[112.4261494174,39.3296107776
            ],"山西省忻州市":[112.7274321931,38.416128061
            ],"山西省太原市":[112.5437100641,37.8701314671
            ],"山西省大同市":[113.2929418393,40.0752133773
            ],"山西省阳泉市":[113.5745931295,37.856168679
            ],"山西省晋中市":[112.746524575,37.6863708473
            ],"山西省长治市":[113.1093260096,36.1953133596
            ],"山西省晋城市":[112.8458754486,35.4910281392
            ],"山西省临汾市":[111.5127458082,36.0881197634
            ],"山西省吕梁市":[111.137609868,37.5176496097
            ],"山西省运城市":[111.0012211119,35.0271542901
            ],"河南省商丘市":[115.650492656,34.4154762671
            ],"河南省郑州市":[113.6189480688,34.7483726297
            ],"河南省安阳市":[114.3868870688,36.0998740133
            ],"河南省新乡市":[113.9210977432,35.3044764993
            ],"河南省许昌市":[113.8461384005,34.0368796277
            ],"河南省平顶山市":[113.1867059274,33.7675813178
            ],"河南省信阳市":[114.0851308116,32.1488529792
            ],"河南省南阳市":[112.5221058366,32.9925983408
            ],"河南省开封市":[114.3021553122,34.7981098276
            ],"河南省洛阳市":[112.4467140504,34.619056698
            ],"河南省焦作市":[113.235647886,35.2155346018
            ],"河南省鹤壁市":[114.2910802162,35.7467732505
            ],"河南省濮阳市":[115.0231266215,35.7618212161
            ],"河南省周口市":[114.6915263156,33.6272876677
            ],"河南省漯河市":[114.0110862712,33.5829810283
            ],"河南省驻马店市":[114.0160924663,33.0135701415
            ],"河南省三门峡市":[111.1940281407,34.7733747116
            ],"辽宁省铁岭市":[123.7199917856,42.2213723033
            ],"辽宁省大连市":[121.6100532808,38.9125212468
            ],"辽宁省鞍山市":[122.9895186894,41.1067846452
            ],"辽宁省抚顺市":[123.9522329085,41.8774591778
            ],"辽宁省本溪市":[123.6801738079,41.4848769436
            ],"辽宁省丹东市":[124.3492276825,39.9989703294
            ],"辽宁省锦州市":[121.132596,41.100931
            ],"辽宁省营口市":[122.241575,40.673137
            ],"辽宁省阜新市":[121.676408,42.028022
            ],"辽宁省辽阳市":[123.243366,41.274161
            ],"辽宁省朝阳市":[120.457499,41.579821
            ],"辽宁省盘锦市":[122.07749,41.125875
            ],"辽宁省葫芦岛":[120.843398,40.717364
            ],"吉林省长春市":[125.330602,43.821954
            ],"吉林省吉林市":[126.555635,43.843568
            ],"吉林省延边市":[129.52052,42.912717
            ],"吉林省四平市":[124.3437490886,43.1635381214
            ],"吉林省通化市":[125.9343415436,41.7260116266
            ],"吉林省白城市":[122.8331686596,45.6176636384
            ],"吉林省辽源市":[125.1375992801,42.8852365268
            ],"吉林省松原市":[124.818686067,45.1393401082
            ],"吉林省白山市":[126.4171388505,41.9314693172
            ],"黑龙江省哈尔滨市":[126.528932686,45.8012018452
            ],"黑龙江省齐齐哈尔市":[123.9114383984,47.3521816734
            ],"黑龙江省牡丹江市":[129.625896237,44.5484109608
            ],"黑龙江省佳木斯市":[130.3131837403,46.7971003124
            ],"黑龙江省绥化市":[126.9626331289,46.6518792443
            ],"黑龙江省黑河市":[127.5217810338,50.2435081177
            ],"黑龙江省大兴安岭地区":[123.630351798,52.5038282583
            ],"黑龙江省伊春市":[128.8339810167,47.7251069024
            ],"黑龙江省大庆市":[125.0949529591,46.5858680332
            ],"黑龙江省七台河市":[130.9973254096,45.7681224718
            ],"黑龙江省鸡西市":[130.9615125953,45.2930865606
            ],"黑龙江省鹤岗市":[130.2901571461,47.3474651085
            ],"黑龙江省双鸭山市":[131.1508753649,46.6446037922
            ],"内蒙古锡林郭勒盟":[116.0411798815,43.9309159794
            ],"内蒙古兴安盟":[122.0318914264,46.0801456549
            ],"内蒙古阿拉善盟":[105.7247047113,38.8514784334
            ],"江苏省无锡市":[120.3074011161,31.4949543558
            ],"江苏省镇江市":[119.4185343589,32.1904938238
            ],"江苏省苏州市":[120.5818049003,31.2994981173
            ],"江苏省南通市":[120.8907847439,31.9830611593
            ],"江苏省扬州市":[119.4074995549,32.3963022259
            ],"江苏省盐城市":[120.1560144177,33.3507739619
            ],"江苏省徐州市":[117.2782392205,34.2082095551
            ],"江苏省淮安市":[119.0095521085,33.6115909198
            ],"江苏省连云港市":[119.2164648635,34.5976115299
            ],"江苏省常州市":[119.9702459619,31.8117500319
            ],"江苏省泰州市":[119.9182546381,32.4571434762
            ],"江苏省宿迁市":[118.2694865722,33.9633352964
            ],"山东省菏泽市":[115.4751217304,35.2340569469
            ],"山东省济南市":[117.1138608116,36.6497817915
            ],"山东省青岛市":[120.3778458118,36.0658569754
            ],"山东省淄博市":[118.0489305123,36.8124625025
            ],"山东省德州市":[116.3531317884,37.4345096242
            ],"山东省烟台市":[121.4427758653,37.4626913218
            ],"山东省潍坊市":[119.1561081161,36.7058674553
            ],"山东省济宁市":[116.5817009023,35.4149519391
            ],"山东省泰安市":[117.0819266166,36.1996838611
            ],"山东省临沂市":[118.3512529532,35.1051776204
            ],"山东省滨州市":[117.9650732486,37.3813048909
            ],"山东省东营市":[118.6693422763,37.4326235731
            ],"安徽省滁州市":[118.3275643416,32.2570033658
            ],"安徽省合肥市":[117.2214998776,31.8223053535
            ],"安徽省蚌埠市":[117.3831120596,32.9173215999
            ],"安徽省芜湖市":[118.4276122313,31.3540265172
            ],"安徽省淮南市":[116.994560822,32.6275548189
            ],"安徽省马鞍山市":[118.5017799626,31.6725742294
            ],"安徽省安庆市":[117.05157779,30.5275995267
            ],"安徽省宿州市":[116.9586344984,33.6477431219
            ],"安徽省阜阳市":[115.8086014104,32.8917940084
            ],"云南省曲靖市":[103.7939649645,25.4931106508
            ],"云南省保山市":[99.1697897107,25.136166777
            ],"云南省玉溪市":[102.5457171237,24.355063922
            ],"云南省普洱市":[100.9648524721,22.8281411377
            ],"云南省临沧市":[100.0877351507,23.8868001002
            ],"云南省丽江市":[100.2248745056,26.858059591
            ],"西藏山南地区":[91.770974663,29.240086764
            ],"西藏阿里地区":[80.1039675488,32.5035818513
            ],"海南省海口市":[110.31463496,20.0403434771
            ],"新疆博尔塔拉蒙古自治州":[82.0632100794,44.9050125134
            ],"陕西省咸阳市":[108.7043240293,34.3305867525
            ],"陕西省延安市":[109.4845843523,36.5853356801
            ],"陕西省榆林市":[109.7294191169,38.2846820783
            ],"陕西省渭南市":[109.5048765525,34.5012634256
            ],"陕西省商洛市":[109.9130410956,33.8740098276
            ],"陕西省安康市":[109.0241457625,32.6868018889
            ],"陕西省汉中市":[107.0184925963,33.0694690043
            ],"陕西省宝鸡市":[107.2332908103,34.3645482211
            ],"陕西省铜川市":[108.9412891216,34.8975101226
            ],"甘肃省兰州市":[103.840521,36.067235
            ],"甘肃省定西市":[104.63242,35.586833
            ],"甘肃省平凉市":[106.671442,35.549232
            ],"甘肃省庆阳市":[107.649386,35.715216
            ],"甘肃省武威市":[102.644554,37.934378
            ],"甘肃省张掖市":[100.456411,38.932066
            ],"甘肃省天水市":[105.731417,34.587412
            ],"甘肃省陇南市":[104.928575,33.40662
            ],"甘肃省白银市":[104.144451,36.550825
            ],"宁夏银川市":[106.238494,38.49246
            ],"宁夏石嘴山市":[106.3906,38.989683
            ],"宁夏吴忠市":[106.205371,38.003713
            ],"宁夏固原市":[106.248577,36.021617
            ],"青海省西宁市":[101.78445,36.623385
            ],"新疆乌鲁木齐":[87.62444,43.830763
            ],"安徽省黄山市":[118.3337599924,29.7181921895
            ],"安徽省淮北市":[116.7925970572,33.9571622701
            ],"安徽省铜陵市":[117.8066637928,30.9474194063
            ],"安徽省宣城市":[118.75389327,30.9429032883
            ],"安徽省六安市":[116.5149132455,31.7371734595
            ],"安徽省池州市":[117.4865142553,30.6669903127
            ],"浙江省衢州市":[118.855210155,28.9729624666
            ],"浙江省杭州市":[120.2044835101,30.2494163927
            ],"浙江省湖州市":[120.0831127141,30.8950975113
            ],"浙江省嘉兴市":[120.7526864145,30.7473423022
            ],"浙江省宁波市":[121.6178926436,29.8630085108
            ],"浙江省绍兴市":[120.5749328967,30.0327500816
            ],"浙江省台州市":[121.4165777076,28.6588895244
            ],"浙江省温州市":[120.6959504539,27.9981131016
            ],"浙江省丽水市":[119.9185859068,28.4709210157
            ],"浙江省金华市":[119.653436,29.084639
            ],"浙江省舟山市":[122.213556,29.990912
            ],"福建省福州市":[119.30347,26.080429
            ],"福建省厦门市":[118.096435,24.485407
            ],"福建省宁德市":[119.554511,26.672242
            ],"福建省莆田市":[119.014521,25.459865
            ],"福建省泉州市":[118.682446,24.879952
            ],"福建省漳州市":[117.6422260471,24.5161570788
            ],"福建省龙岩市":[117.0121517328,25.0777428256
            ],"福建省三明市":[117.6340712119,26.2674082991
            ],"福建省南平市":[118.0478505317,27.2891707168
            ],"山东省威海市":[122.115959744,37.5094889126
            ],"山东省枣庄市":[117.3181056756,34.8108621499
            ],"山东省日照市":[119.5215477661,35.4167602174
            ],"山东省莱芜市":[117.6711243049,36.2137261705
            ],"山东省聊城市":[115.9793343791,36.4570210578
            ],"广东省汕尾市":[115.3700592978,22.7882323483
            ],"广东省阳江市":[111.9769162425,21.8609829585
            ],"广东省揭阳市":[116.3672998178,23.5520452947
            ],"广东省茂名市":[110.9204981073,21.6660715391
            ],"江西省鹰潭市":[117.0637414692,28.2631599501
            ],"湖北省襄阳市":[112.1158953347,32.0110017292
            ],"湖北省鄂州市":[114.8899526367,30.3935862383
            ],"湖北省孝感市":[113.9105121086,30.9272642429
            ],"湖北省黄冈市":[114.8669128707,30.4561088072
            ],"湖北省黄石市":[115.033632615,30.2017072395
            ],"湖北省咸宁市":[114.316608342,29.8436057805
            ],"湖北省荆州市":[112.2332582605,30.337643333
            ],"湖北省宜昌市":[111.2803476288,30.6941311497
            ],"湖北省恩施市":[109.4750819796,30.2978036956
            ],"湖北省十堰市":[110.7926717959,32.6313214137
            ],"湖北省随州市":[113.3767844964,31.6921273327
            ],"湖北省荆门市":[112.1941653004,31.0376229947
            ],"湖北省仙桃市":[113.4489535039,30.3649980954
            ],"湖南省岳阳市":[113.1230629813,29.3596261855
            ],"湖南省长沙市":[112.9335162442,28.2323034791
            ],"湖南省湘潭市":[112.9385603206,27.8330738358
            ],"湖南省株洲市":[113.1281696916,27.8305987605
            ],"湖南省衡阳市":[112.5667455281,26.8969120319
            ],"湖南省郴州市":[113.0095037237,25.773562379
            ],"湖南省常德市":[111.6934003772,29.0345154501
            ],"湖南省益阳市":[112.3492886733,28.5569601521
            ],"湖南省娄底市":[111.989489251,27.7006293894
            ],"湖南省邵阳市":[111.462397958,27.2422411467
            ],"湖南省张家界市":[110.4740797954,29.1197650293
            ],"湖南省怀化市":[109.9973187621,27.5725626096
            ],"湖南省永州市":[111.607742452,26.4232108746
            ],"广东省江门市":[113.0765387964,22.5815322443
            ],"广东省韶关市":[113.5917935162,24.8131751868
            ],"广东省惠州市":[114.4120274806,23.1131784052
            ],"广东省梅州市":[116.1181715327,24.2908873901
            ],"广东省汕头市":[116.6775279967,23.355988358
            ],"广东省深圳市":[114.0528899134,22.5455155546
            ],"广东省珠海市":[113.570950851,22.2739013728
            ],"广东省佛山市":[113.1165184334,23.0243820777
            ],"广东省肇庆市":[112.4597250973,23.0494052021
            ],"广东省湛江市":[110.3545150187,21.2731881758
            ],"广东省中山市":[113.3875170108,22.5186712576
            ],"广东省河源市":[114.6963027712,23.7460928985
            ],"广东省清远市":[113.0505322813,23.6844605976
            ],"广东省云浮市":[112.0396838112,22.9176536896
            ],"广东省潮州市":[116.6186365047,23.6591230603
            ],"广东省东莞市":[113.7467993637,23.0236914965
            ],"广西防城港市":[108.3500080735,21.6891878449
            ],"广西南宁市":[108.3629356575,22.8189739942
            ],"广西柳州市":[109.4233811428,24.3284251266
            ],"广西桂林市":[110.1923991317,25.2397581753
            ],"广西梧州市":[111.2738676258,23.4795693001
            ],"广西玉林市":[110.1775160484,22.6565331379
            ],"广西百色市":[106.6145967319,23.9053884532
            ],"广西钦州市":[108.6502449023,21.9837893841
            ],"广西河池市":[108.0807402461,24.6957612199
            ],"广西北海市":[109.1156832712,21.483439868
            ],"江西省新余市":[114.9121473658,27.8212845585
            ],"江西省南昌市":[115.8530747077,28.6870946488
            ],"江西省九江市":[115.9959247984,29.7079676407
            ],"江西省上饶市":[117.9380255288,28.4577763447
            ],"江西省抚州市":[116.3529955814,27.9522831142
            ],"江西省宜春市":[114.4116843828,27.8182869305
            ],"江西省吉安市":[114.9890132287,27.117169338
            ],"江西省赣州市":[114.9292790313,25.8322751212
            ],"江西省景德镇市":[117.1726666121,29.2712122426
            ],"江西省萍乡市":[113.8496814558,27.625692094
            ],"四川省攀枝花市":[101.717432423,26.5857258079
            ],"四川省自贡市":[104.7753243245,29.342178451
            ],"四川省绵阳市":[104.6763602416,31.4701176109
            ],"四川省南充市":[106.1069611355,30.8401242884
            ],"四川省达州市":[107.4633136703,31.210737931
            ],"四川省遂宁市":[105.5895912565,30.5355838916
            ],"四川省广安市":[106.6292118826,30.4587640669
            ],"四川省巴中市":[106.7408567452,31.8693811995
            ],"四川省泸州市":[105.4384110385,28.8747972457
            ],"四川省宜宾市":[104.6406244887,28.7551207751
            ],"四川省内江市":[105.0548076068,29.5827669378
            ],"四川省乐山市":[103.7637690059,29.5548037112
            ],"四川省雅安市":[103.0410143628,30.0134954913
            ],"四川省德阳市":[104.3953146534,31.1289568432
            ],"四川省广元市":[105.840283405,32.4376856745
            ],"贵州省贵阳市":[106.6264366888,26.6512290693
            ],"贵州省遵义市":[106.92318799,27.7289171118
            ],"贵州省安顺市":[105.9446320606,26.2565928317
            ],"贵州省铜仁市":[109.1764146013,27.6937955766
            ],"云南省昭通市":[103.7149354923,27.3415501737
            ],"云南省昆明市":[102.8445928909,24.8707626627
            ]

        };

        var convertData = function (data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push(geoCoord.concat(data[i].value));
                }
            }
            return res;
        };

        option = {
            backgroundColor: '#1b1e2a',
            title: {
                text: '各地用户活跃度分析',
                subtext: 'data from telecom',
                left: 'center',
                textStyle: {
                    color: '#fff'
                }
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                top: 'bottom',
                left: 'right',
                data:['pm2.5'],
                textStyle: {
                    color: '#fff'
                }
            },
            visualMap: {
                min: 0,
                max: 300,
                splitNumber: 5,
                color: ['#d94e5d','#eac736','#50a3ba'],
                textStyle: {
                    color: '#fff'
                }
            },
            geo: {
                map: 'china',
                label: {
                    emphasis: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        areaColor: '#323c48',
                        borderColor: '#111'
                    },
                    emphasis: {
                        areaColor: '#2a333d'
                    }
                }
            },
            series: [
                {
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: convertData([
                        {name: "<%=cityname[1]%>", value: 9},
                        {name: "鄂尔多斯", value: 12},
                        {name: "招远", value: 12},
                        {name: "舟山", value: 12},
                        {name: "齐齐哈尔", value: 14},
                        {name: "盐城", value: 15},
                        {name: "赤峰", value: 16},
                        {name: "青岛", value: 18},
                        {name: "乳山", value: 18},
                        {name: "金昌", value: 19},
                        {name: "泉州", value: 21},
                        {name: "莱西", value: 21},
                        {name: "日照", value: 21},
                        {name: "胶南", value: 22},
                        {name: "南通", value: 23},
                        {name: "拉萨", value: 24},
                        {name: "云浮", value: 24},
                        {name: "梅州", value: 25},
                        {name: "文登", value: 25},
                        {name: "上海", value: 25},
                        {name: "攀枝花", value: 25},
                        {name: "威海", value: 25},
                        {name: "承德", value: 25},
                        {name: "厦门", value: 26},
                        {name: "汕尾", value: 26},
                        {name: "潮州", value: 26},
                        {name: "丹东", value: 27},
                        {name: "太仓", value: 27},
                        {name: "曲靖", value: 27},
                        {name: "烟台", value: 28},
                        {name: "福州", value: 29},
                        {name: "瓦房店", value: 30},
                        {name: "即墨", value: 30},
                        {name: "抚顺", value: 31},
                        {name: "玉溪", value: 31},
                        {name: "张家口", value: 31},
                        {name: "阳泉", value: 31},
                        {name: "莱州", value: 32},
                        {name: "湖州", value: 32},
                        {name: "汕头", value: 32},
                        {name: "昆山", value: 33},
                        {name: "宁波", value: 33},
                        {name: "湛江", value: 33},
                        {name: "揭阳", value: 34},
                        {name: "荣成", value: 34},
                        {name: "连云港", value: 35},
                        {name: "葫芦岛", value: 35},
                        {name: "常熟", value: 36},
                        {name: "东莞", value: 36},
                        {name: "河源", value: 36},
                        {name: "淮安", value: 36},
                        {name: "泰州", value: 36},
                        {name: "南宁", value: 37},
                        {name: "营口", value: 37},
                        {name: "惠州", value: 37},
                        {name: "江阴", value: 37},
                        {name: "蓬莱", value: 37},
                        {name: "韶关", value: 38},
                        {name: "嘉峪关", value: 38},
                        {name: "广州", value: 38},
                        {name: "延安", value: 38},
                        {name: "太原", value: 39},
                        {name: "清远", value: 39},
                        {name: "中山", value: 39},
                        {name: "昆明", value: 39},
                        {name: "寿光", value: 40},
                        {name: "盘锦", value: 40},
                        {name: "长治", value: 41},
                        {name: "深圳", value: 41},
                        {name: "珠海", value: 42},
                        {name: "宿迁", value: 43},
                        {name: "咸阳", value: 43},
                        {name: "铜川", value: 44},
                        {name: "平度", value: 44},
                        {name: "佛山", value: 44},
                        {name: "海口", value: 44},
                        {name: "江门", value: 45},
                        {name: "章丘", value: 45},
                        {name: "肇庆", value: 46},
                        {name: "大连", value: 47},
                        {name: "临汾", value: 47},
                        {name: "吴江", value: 47},
                        {name: "石嘴山", value: 49},
                        {name: "沈阳", value: 50},
                        {name: "苏州", value: 50},
                        {name: "茂名", value: 50},
                        {name: "嘉兴", value: 51},
                        {name: "长春", value: 51},
                        {name: "胶州", value: 52},
                        {name: "银川", value: 52},
                        {name: "张家港", value: 52},
                        {name: "三门峡", value: 53},
                        {name: "锦州", value: 54},
                        {name: "南昌", value: 54},
                        {name: "柳州", value: 54},
                        {name: "三亚", value: 54},
                        {name: "自贡", value: 56},
                        {name: "吉林", value: 56},
                        {name: "阳江", value: 57},
                        {name: "泸州", value: 57},
                        {name: "西宁", value: 57},
                        {name: "宜宾", value: 58},
                        {name: "呼和浩特", value: 58},
                        {name: "成都", value: 58},
                        {name: "大同", value: 58},
                        {name: "镇江", value: 59},
                        {name: "桂林", value: 59},
                        {name: "张家界", value: 59},
                        {name: "宜兴", value: 59},
                        {name: "北海", value: 60},
                        {name: "西安", value: 61},
                        {name: "金坛", value: 62},
                        {name: "东营", value: 62},
                        {name: "牡丹江", value: 63},
                        {name: "遵义", value: 63},
                        {name: "绍兴", value: 63},
                        {name: "扬州", value: 64},
                        {name: "常州", value: 64},
                        {name: "潍坊", value: 65},
                        {name: "重庆", value: 66},
                        {name: "台州", value: 67},
                        {name: "南京", value: 67},
                        {name: "滨州", value: 70},
                        {name: "贵阳", value: 71},
                        {name: "无锡", value: 71},
                        {name: "本溪", value: 71},
                        {name: "克拉玛依", value: 72},
                        {name: "渭南", value: 72},
                        {name: "马鞍山", value: 72},
                        {name: "宝鸡", value: 72},
                        {name: "焦作", value: 75},
                        {name: "句容", value: 75},
                        {name: "北京", value: 79},
                        {name: "徐州", value: 79},
                        {name: "衡水", value: 80},
                        {name: "包头", value: 80},
                        {name: "绵阳", value: 80},
                        {name: "乌鲁木齐", value: 84},
                        {name: "枣庄", value: 84},
                        {name: "杭州", value: 84},
                        {name: "淄博", value: 85},
                        {name: "鞍山", value: 86},
                        {name: "溧阳", value: 86},
                        {name: "库尔勒", value: 86},
                        {name: "安阳", value: 90},
                        {name: "开封", value: 90},
                        {name: "济南", value: 92},
                        {name: "德阳", value: 93},
                        {name: "温州", value: 95},
                        {name: "九江", value: 96},
                        {name: "邯郸", value: 98},
                        {name: "临安", value: 99},
                        {name: "兰州", value: 99},
                        {name: "沧州", value: 100},
                        {name: "临沂", value: 103},
                        {name: "南充", value: 104},
                        {name: "天津", value: 105},
                        {name: "富阳", value: 106},
                        {name: "泰安", value: 112},
                        {name: "诸暨", value: 112},
                        {name: "郑州", value: 113},
                        {name: "哈尔滨", value: 114},
                        {name: "聊城", value: 116},
                        {name: "芜湖", value: 117},
                        {name: "唐山", value: 119},
                        {name: "平顶山", value: 119},
                        {name: "邢台", value: 119},
                        {name: "德州", value: 120},
                        {name: "济宁", value: 120},
                        {name: "荆州", value: 127},
                        {name: "宜昌", value: 130},
                        {name: "义乌", value: 132},
                        {name: "丽水", value: 133},
                        {name: "洛阳", value: 134},
                        {name: "秦皇岛", value: 136},
                        {name: "株洲", value: 143},
                        {name: "石家庄", value: 147},
                        {name: "莱芜", value: 148},
                        {name: "常德", value: 152},
                        {name: "保定", value: 153},
                        {name: "湘潭", value: 154},
                        {name: "金华", value: 157},
                        {name: "岳阳", value: 169},
                        {name: "长沙", value: 175},
                        {name: "衢州", value: 177},
                        {name: "廊坊", value: 193},
                        {name: "菏泽", value: 194},
                        {name: "合肥", value: 229},
                        {name: "武汉", value: 273},
                        {name: "大庆", value: 279}
                    ]),
                    symbolSize: 12,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    </script>

    </div>
</div>
</body>
</html>

