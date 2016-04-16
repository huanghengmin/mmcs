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
//        title: '���м��',
        columns:[
            {header:'����',width:255,sortable: true,dataIndex:'zonename'},
            {header:'����Ӧ����', width:70,sortable: true,dataIndex:'bizsum'},
            {header:'���ս����û���', width:95,sortable: true, dataIndex:'accesssum'},
            {header:'���ս����ն���', width:Ext.getBody().getWidth()/10-5,sortable: true, dataIndex:'terminalnum'},
            {header:'����������(MB)',width:Ext.getBody().getWidth()/10,sortable: true,  dataIndex:'outflux'},
            {header:'�½����û���',width:85,sortable: true,  dataIndex:'monthaccess'},
            {header:'�½����ն���',width:85,sortable: true, dataIndex:'monthterminalnum'},
            {header:'��������(MB)', width:85,sortable: true,dataIndex:'monthoutflux' },
            {header:'��ϵͳ������(%) ',width:105,sortable: true, dataIndex:'sysrunpresent'}
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
    //�첽�ڵ�
    var root = new Ext.tree.AsyncTreeNode({
        id:'-1',
        text:'���ڵ�'
    });

    tree.setRootNode(root);
//    tree.expandAll();
    tree.render();
    tree.on('click', function(node, event){
        if(node.leaf){  //�����Ҷ�ڵ�
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
        Ext.getCmp('approvematerial').setValue("<a href='javascript;' onclick='download_log(\""+approvematerial+"\");'>�鿴������������</a>");


//        Ext.getCmp('approvematerial').setValue("<a href="+approvematerial+">�鿴������������</a>");
        Ext.getCmp('approveno').setValue(approveno);
        Ext.getCmp('approveunit').setValue(approveunit);
        Ext.getCmp('buildingunitcode').setValue(buildingunitcode);
        Ext.getCmp('constructunit').setValue(constructunit);
        if(ifpassed==1){
            Ext.getCmp('ifpassed').setValue('��');
        }else{
            Ext.getCmp('ifpassed').setValue('��');
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
        Ext.getCmp('securityproject').setValue("<a href="+securityproject+">��ȫ��������</a>");
        if(signsecrecyprotocol==1){
            Ext.getCmp('signsecrecyprotocol').setValue('��');
        }else{
            Ext.getCmp('signsecrecyprotocol').setValue('��');
        }
        Ext.getCmp('systemclass').setValue(systemclass);
        Ext.getCmp('systemmap').setValue("<a href="+systemmap+">ϵͳ����ͼ</a>");
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
                        fieldLabel:'ϵͳ����'
                    }),
                    new Ext.form.DisplayField({
                        name:'manager',
                        id:'manager',
                        fieldLabel:'ϵͳ����Ա����'
                    }),
                    new Ext.form.DisplayField({
                        name:'managerphone',
                        id:'managerphone',
                        fieldLabel:'ϵͳ����Ա��ϵ�绰'
                    }),

                    new Ext.form.DisplayField({
                        name:'managerotherlink',
                        id:'managerotherlink',
                        fieldLabel:'ϵͳ����Ա������ϵ��ʽ'
                    }),
                    new Ext.form.DisplayField({
                        name:'managermail',
                        id:'managermail',
                        fieldLabel:'ϵͳ����Ա�����������ַ'
                    }),
                      new Ext.form.DisplayField({
                        name:'maintainmanager',
                        id:'maintainmanager',
                        fieldLabel:'�ϼ���������'
                    }) ,
                    new Ext.form.DisplayField({
                        name:'maintainmanagerPhone',
                        id:'maintainmanagerPhone',
                        fieldLabel:'�ϼ�������ϵ�绰'
                    }) , new Ext.form.DisplayField({
                        name:'maintainmanagerLink',
                        id:'maintainmanagerLink',
                        fieldLabel:'�ϼ�����������ϵ��ʽ'
                    }),
                    new Ext.form.DisplayField({
                        name:'maintainmanagerMail',
                        id:'maintainmanagerMail',
                        fieldLabel:'�ϼ����ܹ����������ַ'
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
                    fieldLabel:'ϵͳ����'
                }),
                new Ext.form.DisplayField({
                    name:'systemmap',
                    id:'systemmap',
                    fieldLabel:'ϵͳ����ͼ'
                }),
                new Ext.form.DisplayField({
                    name:'approveno',
                    id:'approveno',
                    fieldLabel:'��������'
                }),
                new Ext.form.DisplayField({
                    name:'approveunit',
                    id:'approveunit',
                    fieldLabel:'������λ'
                }),
                new Ext.form.DisplayField({
                    name:'approvetime',
                    id:'approvetime',
                    fieldLabel:'����ʱ��'
                }),
                new Ext.form.DisplayField({
                    name:'ifpassed',
                    id:'ifpassed',
                    fieldLabel:'�Ƿ�������ͨ��'
                }),
                new Ext.form.DisplayField({
                    name:'approvematerial',
                    id:'approvematerial',
                    fieldLabel:'������������'
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
                    fieldLabel:'����λ����Ϣ'
                }),
                new Ext.form.DisplayField({
                    name:'remoteaccessip',
                    id:'remoteaccessip',
                    fieldLabel:'�����ϵͳ������IP��ַ'
                }),
                new Ext.form.DisplayField({
                    name:'buildingunitcode',
                    id:'buildingunitcode',
                    fieldLabel:'��Ҫ�н���λ'
                }),
                new Ext.form.DisplayField({
                    name:'constructunit',
                    id:'constructunit',
                    fieldLabel:'���赥λ'
                }),
                new Ext.form.DisplayField({
                    name:'buildingtime',
                    id:'buildingtime',
                    fieldLabel:'����ʱ��'
                }),
                new Ext.form.DisplayField({
                    name:'maintainunit',
                    id:'maintainunit',
                    fieldLabel:'��Ӫ��λ����'
                }),
                new Ext.form.DisplayField({
                    name:'securityproject',
                    id:'securityproject',
                    fieldLabel:'��ȫ��������'
                }),
                new Ext.form.DisplayField({
                    name:'signsecrecyprotocol',
                    id:'signsecrecyprotocol',
                    fieldLabel:'�Ƿ�ǩ����Э��'
                }),

                new Ext.form.DisplayField({
                    name:'collecttime',
                    id:'collecttime',
                    fieldLabel:'ͳ��ʱ��'
                })

            ]

        }]
    });
    //todo     ��·��
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
    var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var colM = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,hidden:true,menuDisabled:true,width:100},
//           {header:"ϵͳ��ʶ",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�ⲿ��·˵��",			dataIndex:"outlinkdisc",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�ⲿ��·�ṩ��",			dataIndex:"outlinkvender",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�ⲿ��·��ʶ",			dataIndex:"idoutconnlink",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�ⲿ��·����",			dataIndex:"outlinkbandwidth",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"ͳ��ʱ��",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        /*,
         {header:'�������',		dataIndex:'id',		  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}*/

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"��{0}������{1}������{2}��",
        emptyMsg:"û�м�¼",
        beforePageText:"��ǰҳ",
        afterPageText:"��{0}ҳ"
    });
    var grid_panel = new Ext.grid.GridPanel({
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colM,
        store:store,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }) ,
        bbar:page_toolbar
    });
    //todo    �豸��
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
//           {header:"ϵͳ��ʶ",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"������������/�ͺ�",			dataIndex:"brandtyoe",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�豸��ע",			dataIndex:"devicedesc",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�豸ip",			dataIndex:"deviceIP",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�豸���ʹ���",			dataIndex:"devicetypecode",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�豸id",			dataIndex:"iddevice",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"ͳ��ʱ��",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        /*,
         {header:'�������',		dataIndex:'id',		  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}*/

    ]);
    var page_toolbardevice = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:storedevice,
        displayInfo:true,
        displayMsg:"��{0}������{1}������{2}��",
        emptyMsg:"û�м�¼",
        beforePageText:"��ǰҳ",
        afterPageText:"��{0}ҳ"
    });
    var grid_paneldevice = new Ext.grid.GridPanel({
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colMdevice,
        store:storedevice,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }) ,
        bbar:page_toolbardevice
    });
    //todo Ӧ�ñ�
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
//           {header:"ϵͳ��ʶ",			dataIndex:"idsystem",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"Ӧ��ģʽ",			dataIndex:"bizMode",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�Ƿ��ѱ���",			dataIndex:"backup",  align:'center',sortable:true,menuDisabled:true,width:100,renderer : function(value) {
            if (value == '1') {
                return '��';
            }  else {
                return '��';
            }
        }},
        {header:"������������",			dataIndex:"backupUnitname",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"Ӧ������",			dataIndex:"bizType",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"��������",			dataIndex:"approveNo",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"������������",			dataIndex:"approveMaterial",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"������λ",			dataIndex:"approveUnit",  align:'center',sortable:true,menuDisabled:true,width:100},
//            {header:"����Ӧ����ʽ",			dataIndex:"bizfunctype",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"Ӧ�����ܲ�������",			dataIndex:"bizManagedepart",  align:'center',sortable:true,menuDisabled:true,width:100},
//            {header:"����Ӧ���漰���ƶ��ն�",			dataIndex:"bizTerminal",  align:'center',sortable:true,menuDisabled:true,width:100},

//           {header:"�ϼ����ܹ����������ַ",			dataIndex:"dateexchangeDateflux",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"����Ӧ�����ܲ�����������·",			dataIndex:"managedepartLink",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"Ӧ�����ܲ���������E-mail",			dataIndex:"managedepartMail",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"����Ӧ�����ܲ�������������",			dataIndex:"managedepartManager",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"����Ӧ�����ܲ�����������ϵ�绰",			dataIndex:"managedepartPhone",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"�Ƿ���ʵʱ��",			dataIndex:"realtime",  align:'center',sortable:true,menuDisabled:true,width:100,renderer : function(value) {
//               if (value == '1') {
//                   return '��';
//               }  else {
//                   return '��';
//               }
//           }},
//           {header:"����ͼ",			dataIndex:"getTopologyMap",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"Ӧ��id",			dataIndex:"getIdbiz",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"����ʱ��",			dataIndex:"approveTime",  align:'center',sortable:true,menuDisabled:true,width:100},
//           {header:"����ʱ��",			dataIndex:"registerTime",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"ͳ��ʱ��",			dataIndex:"collecttime",  align:'center',sortable:true,menuDisabled:true,width:100}
        ,
        {header:'�������',				  align:'center',sortable:true,menuDisabled:true, renderer:show_flag,	width:100}

    ]);
    var page_toolbarbiz = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:storebiz,
        displayInfo:true,
        displayMsg:"��{0}������{1}������{2}��",
        emptyMsg:"û�м�¼",
        beforePageText:"��ǰҳ",
        afterPageText:"��{0}ҳ"
    });
    var grid_panelbiz = new Ext.grid.GridPanel({
        id:'grid_panelbiz.info',
        plain:true,
        height:100,
        animCollapse:true,
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colMbiz,
        store:storebiz,
        stripeRows:true,
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }) ,
        bbar:page_toolbarbiz
    });
    //todo ��ʾ��
    var select_Win = new Ext.Window({
        title:"�鿴���м��",
        width:911,
        autoScroll:true,
        height:getMonitorHeight(),
        modal:true,
        closeAction: "close",  // �رհ�ťʱִ������ ,"close"�رմ���ʱ���ٶ���
//        closable: false, // ��ֹ�û��رմ���
        draggable: false, // ��ֹ�û������϶�����
        maximizable: true,  // �������
//        minimizable: true,  // ������С��
        items:[{
            title:'ע���',
            frame:true,
            height:280,
            items:[formPanel]
        },{
            title:'��·��',
            frame:true,
            autoScroll:true,
            items:[grid_panel]
        } ,{
            title:'�豸��',
            frame:true,
            items:[grid_paneldevice]
        } ,{
            title:'����Ӧ��',
            frame:true,
            items:[grid_panelbiz]
        }
        ]
    });
    select_Win.show();
}
function show_flag(value,p,r){
    return String.format( '<a href="javascript:void(0);" onclick="selectbiz();"><font color="#fa8072">�鿴����Ӧ��</font></a>');
}
function selectbiz(){
    var  grid_panel = Ext.getCmp("grid_panelbiz.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var backup;
    if(recode.get("backup")==1){
        backup='��'
    }else{
        backup='��'
    }
    var realtime;
    if(recode.get("realtime")==1){
        realtime='��'
    }else{
        realtime='��'
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
                fieldLabel:'ϵͳ��ʶ',
                value:recode.get("idsystem")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'�Ƿ��ѱ���' ,
                value:backup
            }),
            new Ext.form.DisplayField({
                fieldLabel:'������������' ,
                value:recode.get("backupUnitname")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'��������'   ,
                value:recode.get("approveNo")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'������������' ,
                value:'<a href='+recode.get("approveMaterial")+'>������������</a>'
            }),
            new Ext.form.DisplayField({
                fieldLabel:'������λ',
                value:recode.get("approveUnit")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ����ʽ',
                value:recode.get("bizfunctype")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ�����ܲ�������'  ,
                value:recode.get("bizManagedepart")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ��ģʽ'       ,
                value:recode.get("bizMode")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ���漰���ƶ��ն�'   ,
                value:recode.get("bizTerminal")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ������'           ,
                value:recode.get("bizType")
            }),
            /* new Ext.form.DisplayField({
             fieldLabel:'�ϼ����ܹ����������ַ'      ,
             value:recode.get("dateexchangeDateflux")
             }),*/
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ�����ܲ�����������·',
                value:recode.get("managedepartLink")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ�����ܲ��������˹�����E-mail' ,
                value:recode.get("managedepartMail")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ�����ܲ�������������' ,
                value:recode.get("managedepartManager")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ�����ܲ�����������ϵ�绰'   ,
                value:recode.get("managedepartPhone")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'�Ƿ���ʵʱ��'            ,
                value:realtime
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����ͼ'            ,
                value:'<a href='+recode.get("getTopologyMap")+'>����ͼ</a>'
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ӧ��id'       ,
                value:recode.get("getIdbiz")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����ʱ��'       ,
                value:recode.get("approveTime")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����ʱ��'        ,
                value:recode.get("registerTime")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ͳ��ʱ��' ,
                value:recode.get("collecttime")
            })
        ]
    });

    var select_Win = new Ext.Window({
        title:"�鿴����Ӧ����ϸ",
        width:600,
        autoScroll:true,
        layout:'fit',
        height:400,
        items:formPanel
    });
    select_Win.show();
}
//    window.open()��ʽ
function ShowDialog(url) {
    var iWidth=300; //���ڿ��
    var iHeight=200;//���ڸ߶�
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


