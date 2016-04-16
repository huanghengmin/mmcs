Ext.onReady(function(){
    document.body.innerHTML="<div id='ctree'></div>";
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var tree = new Ext.tree.ColumnTree({
        id:'1',
        el:'ctree',
        loadMask : true,
        rootVisible:false,
        autoScroll:true,
        expandable:false,
        border:false,
//        title: '运行监控',
        columns:[
            {header:'地区',width:255,sortable: true,dataIndex:'zonename'},
            {header:'接入应用数', width:70,sortable: true,dataIndex:'bizsum'},
            {header:'昨日接入用户数', width:95,sortable: true, dataIndex:'accesssum'},
            {header:'昨日接入终端数', width:Ext.getBody().getWidth()/10-5,sortable: true, dataIndex:'terminalnum'},
            {header:'昨日总流量(MB)',width:Ext.getBody().getWidth()/10,sortable: true,  dataIndex:'outflux'},
            {header:'月接入用户数',width:85,sortable: true,  dataIndex:'monthaccess'},
            {header:'月接入终端数',width:85,sortable: true, dataIndex:'monthterminalnum'},
            {header:'月总流量(MB)', width:85,sortable: true,dataIndex:'monthoutflux' },
            {header:'月系统正常率(%) ',width:105,sortable: true, dataIndex:'sysrunpresent'}
        ],
        loader: new Ext.tree.TreeLoader({
            dataUrl :'ColumtreeAction_selectall2.action',
            timeout:5*60*1000,
            preloadChildren: true,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        })

    });
    //异步节点
    var root = new Ext.tree.AsyncTreeNode({
        id:'-1',
        text:'根节点'
    });

    tree.setRootNode(root);
//    tree.expandAll();
    tree.render();
    tree.on('click', function(node, event){
        if(node.leaf){  //如果是叶节点
            show_score(node);
        }else{
            // show_score(node);
        }
    });
});
function getMonitorHeight() {
    return (document.body.clientHeight-8)/2
}
function show_score(node){
    var record11 = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'idsystem',			mapping:'idsystem'},
        {name:'address',			mapping:'address'},
        {name:'approvematerial',			mapping:'approvematerial'},
        {name:'approveno',			mapping:'approveno'},
        {name:'approveunit',			mapping:'approveunit'},
        {name:'buildingunitcode',			mapping:'buildingunitcode'},
        {name:'constructunit',			mapping:'constructunit'},
        {name:'ifpassed',			mapping:'ifpassed'},
        {name:'maintainmanager',			mapping:'maintainmanager'},
        {name:'maintainmanagerLink',			mapping:'maintainmanagerLink'},
        {name:'maintainmanagerMail',			mapping:'maintainmanagerMail'},
        {name:'maintainmanagerPhone',			mapping:'maintainmanagerPhone'},
        {name:'maintainunit',			mapping:'maintainunit'},
        {name:'manager',			mapping:'manager'},
        {name:'managermail',			mapping:'managermail'},
        {name:'managerotherlink',			mapping:'managerotherlink'},
        {name:'managerphone',			mapping:'managerphone'},
        {name:'remoteaccessip',			mapping:'remoteaccessip'},
        {name:'securityproject',			mapping:'securityproject'},
        {name:'signsecrecyprotocol',			mapping:'signsecrecyprotocol'},
        {name:'systemclass',			mapping:'systemclass'},
        {name:'systemmap',			mapping:'systemmap'},
        {name:'systemname',			mapping:'systemname'},
        {name:'approvetime',			mapping:'approvetime'},
        {name:'buildingtime',			mapping:'buildingtime'},
        {name:'collecttime',			mapping:'collecttime'}
    ]);
    var ds=new Ext.data.Store({
//                url:"SelectAction_selectByIdsystem.action",
        url:"SelectAction_selectByIdsystem.action?idsystem="+node.attributes.idsystem,
        reader:new Ext.data.JsonReader({
            totalProperty:'total',
            root:'rows'
        },record11)});

    ds.load();
    ds.on('load',function(){
        var idsystem = ds.getAt(0).get('idsystem');
        var address = ds.getAt(0).get('address');
        var approvematerial = ds.getAt(0).get('approvematerial');
        var approveno = ds.getAt(0).get('approveno');
        var approveunit = ds.getAt(0).get('approveunit');
        var buildingunitcode = ds.getAt(0).get('buildingunitcode');
        var constructunit = ds.getAt(0).get('constructunit');
        var ifpassed = ds.getAt(0).get('ifpassed');
        var maintainmanager = ds.getAt(0).get('maintainmanager');
        var maintainmanagerLink = ds.getAt(0).get('maintainmanagerLink');
        var maintainmanagerMail = ds.getAt(0).get('maintainmanagerMail');
        var maintainmanagerPhone = ds.getAt(0).get('maintainmanagerPhone');
        var maintainunit = ds.getAt(0).get('maintainunit');
        var manager = ds.getAt(0).get('manager');
        var managermail = ds.getAt(0).get('managermail');
        var managerotherlink = ds.getAt(0).get('managerotherlink');
        var managerphone = ds.getAt(0).get('managerphone');
        var remoteaccessip = ds.getAt(0).get('remoteaccessip');
        var securityproject = ds.getAt(0).get('securityproject');
        var signsecrecyprotocol = ds.getAt(0).get('signsecrecyprotocol');
        var systemclass = ds.getAt(0).get('systemclass');
        var systemmap = ds.getAt(0).get('systemmap');
        var systemname = ds.getAt(0).get('systemname');
        var approvetime = ds.getAt(0).get('approvetime');
        var buildingtime = ds.getAt(0).get('buildingtime');
        var collecttime = ds.getAt(0).get('collecttime');

//            Ext.getCmp('idsystem').setValue(idsystem);
        Ext.getCmp('address').setValue(address);
        Ext.getCmp('approvematerial').setValue("<a href='javascript;' onclick='download_log(\""+approvematerial+"\");'>查看审批材料名称</a>");


//        Ext.getCmp('approvematerial').setValue("<a href="+approvematerial+">查看审批材料名称</a>");
        Ext.getCmp('approveno').setValue(approveno);
        Ext.getCmp('approveunit').setValue(approveunit);
        Ext.getCmp('buildingunitcode').setValue(buildingunitcode);
        Ext.getCmp('constructunit').setValue(constructunit);
        if(ifpassed==1){
            Ext.getCmp('ifpassed').setValue('是');
        }else{
            Ext.getCmp('ifpassed').setValue('否');
        }
        Ext.getCmp('maintainmanager').setValue(maintainmanager);
        Ext.getCmp('maintainmanagerLink').setValue(maintainmanagerLink);
        Ext.getCmp('maintainmanagerMail').setValue(maintainmanagerMail);
        Ext.getCmp('maintainmanagerPhone').setValue(maintainmanagerPhone);
        Ext.getCmp('maintainunit').setValue(maintainunit);
        Ext.getCmp('manager').setValue(manager);
        Ext.getCmp('managermail').setValue(managermail);
        Ext.getCmp('managerotherlink').setValue(managerotherlink);
        Ext.getCmp('managerphone').setValue(managerphone);
        Ext.getCmp('remoteaccessip').setValue(remoteaccessip);
        Ext.getCmp('securityproject').setValue("<a href="+securityproject+">安全技术方案</a>");
        if(signsecrecyprotocol==1){
            Ext.getCmp('signsecrecyprotocol').setValue('是');
        }else{
            Ext.getCmp('signsecrecyprotocol').setValue('否');
        }
        Ext.getCmp('systemclass').setValue(systemclass);
        Ext.getCmp('systemmap').setValue("<a href="+systemmap+">系统拓扑图</a>");
        Ext.getCmp('systemname').setValue(systemname);
        Ext.getCmp('approvetime').setValue(approvetime);
        Ext.getCmp('buildingtime').setValue(buildingtime);
        Ext.getCmp('collecttime').setValue(collecttime);
    });
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        layout:'column',
        height:350,
        border:false,
        items:[{
            plain:true,
            columnWidth :.37,
            border:false,
            layout: 'form',
            items:[{
                plain:true,
                labelAlign:'left',
                labelWidth:150,
                defaultType:'textfield',
                border:false,
                layout: 'form',
                items:[
                    new Ext.form.DisplayField({
                        name:'systemname',
                        id:'systemname',
                        fieldLabel:'系统名称'
                    }),
                    new Ext.form.DisplayField({
                        name:'manager',
                        id:'manager',
                        fieldLabel:'系统管理员姓名'
                    }),
                    new Ext.form.DisplayField({
                        name:'managerphone',
                        id:'managerphone',
                        fieldLabel:'系统管理员联系电话'
                    }),

                    new Ext.form.DisplayField({
                        name:'managerotherlink',
                        id:'managerotherlink',
                        fieldLabel:'系统管理员其他联系方式'
                    }),
                    new Ext.form.DisplayField({
                        name:'managermail',
                        id:'managermail',
                        fieldLabel:'系统管理员公安网邮箱地址'
                    }),
                      new Ext.form.DisplayField({
                        name:'maintainmanager',
                        id:'maintainmanager',
                        fieldLabel:'上级主管姓名'
                    }) ,
                    new Ext.form.DisplayField({
                        name:'maintainmanagerPhone',
                        id:'maintainmanagerPhone',
                        fieldLabel:'上级主管联系电话'
                    }) , new Ext.form.DisplayField({
                        name:'maintainmanagerLink',
                        id:'maintainmanagerLink',
                        fieldLabel:'上级主管其他联系方式'
                    }),
                    new Ext.form.DisplayField({
                        name:'maintainmanagerMail',
                        id:'maintainmanagerMail',
                        fieldLabel:'上级主管公安网邮箱地址'
                    })

                ]
            }]
        },{
            plain:true,
            defaultType:'textfield',
            columnWidth :.3,
            labelAlign:'left',
            labelWidth:120,
            border:false,
            layout: 'form',
            items:[
                new Ext.form.DisplayField({
                    name:'systemclass',
                    id:'systemclass',
                    fieldLabel:'系统类型'
                }),
                new Ext.form.DisplayField({
                    name:'systemmap',
                    id:'systemmap',
                    fieldLabel:'系统拓扑图'
                }),
                new Ext.form.DisplayField({
                    name:'approveno',
                    id:'approveno',
                    fieldLabel:'审批批号'
                }),
                new Ext.form.DisplayField({
                    name:'approveunit',
                    id:'approveunit',
                    fieldLabel:'审批单位'
                }),
                new Ext.form.DisplayField({
                    name:'approvetime',
                    id:'approvetime',
                    fieldLabel:'审批时间'
                }),
                new Ext.form.DisplayField({
                    name:'ifpassed',
                    id:'ifpassed',
                    fieldLabel:'是否已审批通过'
                }),
                new Ext.form.DisplayField({
                    name:'approvematerial',
                    id:'approvematerial',
                    fieldLabel:'审批材料名称'
                })



            ]

        },{
            plain:true,
            defaultType:'textfield',
            columnWidth :.33,
            labelAlign:'left',
            labelWidth:150,
            border:false,
            layout: 'form',
            items:[
                new Ext.form.DisplayField({
                    name:'address',
                    id:'address',
                    fieldLabel:'物理位置信息'
                }),
                new Ext.form.DisplayField({
                    name:'remoteaccessip',
                    id:'remoteaccessip',
                    fieldLabel:'本监控系统公安网IP地址'
                }),
                new Ext.form.DisplayField({
                    name:'buildingunitcode',
                    id:'buildingunitcode',
                    fieldLabel:'主要承建单位'
                }),
                new Ext.form.DisplayField({
                    name:'constructunit',
                    id:'constructunit',
                    fieldLabel:'建设单位'
                }),
                new Ext.form.DisplayField({
                    name:'buildingtime',
                    id:'buildingtime',
                    fieldLabel:'建设时间'
                }),
                new Ext.form.DisplayField({
                    name:'maintainunit',
                    id:'maintainunit',
                    fieldLabel:'运营单位名称'
                }),
                new Ext.form.DisplayField({
                    name:'securityproject',
                    id:'securityproject',
                    fieldLabel:'安全技术方案'
                }),
                new Ext.form.DisplayField({
                    name:'signsecrecyprotocol',
                    id:'signsecrecyprotocol',
                    fieldLabel:'是否签署保密协议'
                }),

                new Ext.form.DisplayField({
                    name:'collecttime',
                    id:'collecttime',
                    fieldLabel:'统计时间'
                })

            ]

        }]
    });
    //todo     链路表
    var start = 0;
    var pageSize = 2;
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'idsystem',			mapping:'idsystem'},
        {name:'outlinkdisc',			mapping:'outlinkdisc'},
        {name:'outlinkvender',			mapping:'outlinkvender'},
        {name:'idoutconnlink',			mapping:'idoutconnlink'},
        {name:'outlinkbandwidth',			mapping:'outlinkbandwidth'},
        {name:'collecttime',			mapping:'collecttime'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"SelectAction_selectByIdsystemOutLink.action?idsystem="+node.attributes.idsystem
//           url:"SelectAction_selectByIdsystemOutLink.action"
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:'total',
        root:'rows'
    },record);
    var store = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    store.load({
        params:{
            start:start,limit:pageSize
        }
    });
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,hidden:true,menuDisabled:true,width:100},
//           {header:"系统标识",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"外部链路说明",			dataIndex:"outlinkdisc",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"外部链路提供商",			dataIndex:"outlinkvender",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"外部链路标识",			dataIndex:"idoutconnlink",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"外部链路带宽",			dataIndex:"outlinkbandwidth",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"统计时间",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        /*,
         {header:'操作标记',		dataIndex:'id',		  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}*/

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"第{0}条到第{1}条，共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var grid_panel = new Ext.grid.GridPanel({
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colM,
        store:store,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }) ,
        bbar:page_toolbar
    });
    //todo    设备表
    var recorddevice = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'idsystem',			mapping:'idsystem'},
        {name:'brandtyoe',			mapping:'brandtyoe'},
        {name:'devicedesc',			mapping:'devicedesc'},
        {name:'deviceIP',			mapping:'deviceIP'},
        {name:'devicetypecode',			mapping:'devicetypecode'},
        {name:'iddevice',			mapping:'iddevice'},
        {name:'collecttime',			mapping:'collecttime'}
    ]);
    var proxydevice = new Ext.data.HttpProxy({
        url:"SelectAction_selectByIdsystemDevice.action?idsystem="+node.attributes.idsystem
//           url:"SelectAction_selectByIdsystemDevice.action"
    });
    var readerdevice = new Ext.data.JsonReader({
        totalProperty:'total',
        root:'rows'
    },recorddevice);
    var storedevice = new Ext.data.GroupingStore({
        proxy : proxydevice,
        reader : readerdevice
    });
    storedevice.load({
        params:{
            start:start,limit:pageSize
        }
    });
    var colMdevice = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,hidden:true,menuDisabled:true,width:100},
//           {header:"系统标识",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"生产厂家名称/型号",			dataIndex:"brandtyoe",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"设备备注",			dataIndex:"devicedesc",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"设备ip",			dataIndex:"deviceIP",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"设备类型代码",			dataIndex:"devicetypecode",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"设备id",			dataIndex:"iddevice",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"统计时间",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        /*,
         {header:'操作标记',		dataIndex:'id',		  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}*/

    ]);
    var page_toolbardevice = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:storedevice,
        displayInfo:true,
        displayMsg:"第{0}条到第{1}条，共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var grid_paneldevice = new Ext.grid.GridPanel({
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colMdevice,
        store:storedevice,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }) ,
        bbar:page_toolbardevice
    });
    //todo 应用表
    var recordbiz = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'ba',			mapping:'id'},
        {name:'idsystem',			mapping:'idsystem'},
        {name:'approveMaterial',			mapping:'approveMaterial'},
        {name:'approveNo',			mapping:'approveNo'},
        {name:'approveUnit',			mapping:'approveUnit'},
        {name:'backup',			mapping:'backup'},
        {name:'backupUnitname',			mapping:'backupUnitname'},
        {name:'bizfunctype',			mapping:'bizfunctype'},
        {name:'bizManagedepart',			mapping:'bizManagedepart'},
        {name:'bizMode',			mapping:'bizMode'},
        {name:'bizTerminal',			mapping:'bizTerminal'},
        {name:'bizType',			mapping:'bizType'},
        {name:'dateexchangeDateflux',			mapping:'dateexchangeDateflux'},
        {name:'managedepartLink',			mapping:'managedepartLink'},
        {name:'managedepartMail',			mapping:'managedepartMail'},
        {name:'managedepartManager',			mapping:'managedepartManager'},
        {name:'managedepartPhone',			mapping:'managedepartPhone'},
        {name:'realtime',			mapping:'realtime'},
        {name:'getTopologyMap',			mapping:'getTopologyMap'},
        {name:'getIdbiz',			mapping:'getIdbiz'},
        {name:'approveTime',			mapping:'approveTime'},
        {name:'registerTime',			mapping:'registerTime'},
        {name:'collecttime',			mapping:'collecttime'}
    ]);
    var proxybiz = new Ext.data.HttpProxy({
        url:"SelectAction_selectByIdsystemBiz.action?idsystem="+node.attributes.idsystem
//           url:"SelectAction_selectByIdsystemBiz.action"
    });
    var readerbiz = new Ext.data.JsonReader({
        totalProperty:'total',
        root:'rows'
    },recordbiz);
    var storebiz = new Ext.data.GroupingStore({
        proxy : proxybiz,
        reader : readerbiz
    });
    storebiz.load({
        params:{
            start:start,limit:pageSize
        }
    });
    var colMbiz = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,hidden:true,menuDisabled:true,width:100},
//           {header:"系统标识",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"应用模式",			dataIndex:"bizMode",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"是否已备案",			dataIndex:"backup",  align:'center',sortable:true,menuDisabled:true,width:100,renderer : function(value) {
            if (value == '1') {
                return '是';
            }  else {
                return '否';
            }
        }},
        {header:"备案部门名称",			dataIndex:"backupUnitname",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"应用类型",			dataIndex:"bizType",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"审批批号",			dataIndex:"approveNo",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"审批材料名称",			dataIndex:"approveMaterial",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"审批单位",			dataIndex:"approveUnit",  align:'center',sortable:true,menuDisabled:true,width:100},
//            {header:"接入应用形式",			dataIndex:"bizfunctype",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"应用主管部门名称",			dataIndex:"bizManagedepart",  align:'center',sortable:true,menuDisabled:true,width:100},
//            {header:"接入应用涉及的移动终端",			dataIndex:"bizTerminal",  align:'center',sortable:true,menuDisabled:true,width:100},

//           {header:"上级主管公安网邮箱地址",			dataIndex:"dateexchangeDateflux",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"接入应用主管部门主管人链路",			dataIndex:"managedepartLink",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"应用主管部门主管人E-mail",			dataIndex:"managedepartMail",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"接入应用主管部门主管人姓名",			dataIndex:"managedepartManager",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"接入应用主管部门主管人联系电话",			dataIndex:"managedepartPhone",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"是否有实时性",			dataIndex:"realtime",  align:'center',sortable:true,menuDisabled:true,width:100,renderer : function(value) {
//               if (value == '1') {
//                   return '是';
//               }  else {
//                   return '否';
//               }
//           }},
//           {header:"拓扑图",			dataIndex:"getTopologyMap",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"应用id",			dataIndex:"getIdbiz",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"审批时间",			dataIndex:"approveTime",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"建设时间",			dataIndex:"registerTime",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"统计时间",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        ,
        {header:'操作标记',				  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}

    ]);
    var page_toolbarbiz = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:storebiz,
        displayInfo:true,
        displayMsg:"第{0}条到第{1}条，共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var grid_panelbiz = new Ext.grid.GridPanel({
        id:'grid_panelbiz.info',
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colMbiz,
        store:storebiz,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }) ,
        bbar:page_toolbarbiz
    });
    //todo 显示块
    var select_Win = new Ext.Window({
        title:"查看运行监控",
        width:911,
        autoScroll:true,
        height:getMonitorHeight(),
        modal:true,
        closeAction: "close",  // 关闭按钮时执行隐藏 ,"close"关闭窗口时销毁对象
//        closable: false, // 禁止用户关闭窗口
        draggable: false, // 禁止用户随意拖动窗口
        maximizable: true,  // 窗口最大化
//        minimizable: true,  // 窗口最小化
        items:[{
            title:'注册表',
            frame:true,
            height:280,
            items:[formPanel]
        },{
            title:'链路表',
            frame:true,
            autoScroll:true,
            items:[grid_panel]
        } ,{
            title:'设备表',
            frame:true,
            items:[grid_paneldevice]
        } ,{
            title:'接入应用',
            frame:true,
            items:[grid_panelbiz]
        }
        ]
    });
    select_Win.show();
}
function show_flag(value,p,r){
    return String.format( '<a href="javascript:void(0);" onclick="selectbiz();"><font color="#fa8072">查看接入应用</font></a>');
}
function selectbiz(){
    var  grid_panel = Ext.getCmp("grid_panelbiz.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var backup;
    if(recode.get("backup")==1){
        backup='是'
    }else{
        backup='否'
    }
    var realtime;
    if(recode.get("realtime")==1){
        realtime='是'
    }else{
        realtime='否'
    }
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:300,
//        baseCls : 'x-plain',
        labelAlign:'center',
        layout:'form',
        border:false,
//        defaults:{
//            width:250
//        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel:'系统标识',
                value:recode.get("idsystem")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'是否已备案' ,
                value:backup
            }),
            new Ext.form.DisplayField({
                fieldLabel:'备案部门名称' ,
                value:recode.get("backupUnitname")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'审批批号'   ,
                value:recode.get("approveNo")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'审批材料名称' ,
                value:'<a href='+recode.get("approveMaterial")+'>审批材料名称</a>'
            }),
            new Ext.form.DisplayField({
                fieldLabel:'审批单位',
                value:recode.get("approveUnit")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用形式',
                value:recode.get("bizfunctype")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用主管部门名称'  ,
                value:recode.get("bizManagedepart")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用模式'       ,
                value:recode.get("bizMode")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用涉及的移动终端'   ,
                value:recode.get("bizTerminal")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用类型'           ,
                value:recode.get("bizType")
            }),
            /* new Ext.form.DisplayField({
             fieldLabel:'上级主管公安网邮箱地址'      ,
             value:recode.get("dateexchangeDateflux")
             }),*/
            new Ext.form.DisplayField({
                fieldLabel:'接入应用主管部门主管人链路',
                value:recode.get("managedepartLink")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用主管部门主管人公安网E-mail' ,
                value:recode.get("managedepartMail")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用主管部门主管人姓名' ,
                value:recode.get("managedepartManager")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用主管部门主管人联系电话'   ,
                value:recode.get("managedepartPhone")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'是否有实时性'            ,
                value:realtime
            }),
            new Ext.form.DisplayField({
                fieldLabel:'拓扑图'            ,
                value:'<a href='+recode.get("getTopologyMap")+'>拓扑图</a>'
            }),
            new Ext.form.DisplayField({
                fieldLabel:'接入应用id'       ,
                value:recode.get("getIdbiz")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'审批时间'       ,
                value:recode.get("approveTime")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'建设时间'        ,
                value:recode.get("registerTime")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'统计时间' ,
                value:recode.get("collecttime")
            })
        ]
    });

    var select_Win = new Ext.Window({
        title:"查看接入应用详细",
        width:600,
        autoScroll:true,
        layout:'fit',
        height:400,
        items:formPanel
    });
    select_Win.show();
}
//    window.open()方式
function ShowDialog(url) {
    var iWidth=300; //窗口宽度
    var iHeight=200;//窗口高度
    var iTop=(window.screen.height-iHeight)/2;
    var iLeft=(window.screen.width-iWidth)/2;
    window.open(url,"Detail","Scrollbars=no,Toolbar=no,Location=no,Direction=no,Resizeable=no, Width="+iWidth+" ,Height="+iHeight+",top="+iTop+",left="+iLeft);
}

function download_log(fileName){
   if (!Ext.fly('test')) {
        var frm = document.createElement('form');
        frm.id = 'test';
        frm.name = id;
        frm.style.display = 'none';
        document.body.appendChild(frm);
   }

   Ext.Ajax.request({
        url: '../../SelectAction_downloadByFilename.action',
        params:{fileName:fileName},
        form: Ext.fly('test'),
        method: 'POST',
        isUpload: true
   });
}


