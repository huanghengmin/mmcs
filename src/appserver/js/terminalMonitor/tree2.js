Ext.onReady(function () {

    document.body.innerHTML = "<div id='ctree'></div>";
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var tree = new Ext.tree.ColumnTree({
        el:'ctree',
        loadMask:true,
        rootVisible:false,
        autoScroll:true,
        expandable:false,
        border:false,
        enableDD:true,
        animate:true,
//     title: '运行监控',
        columns:[
            {id:'zone', header:"地区(集中监控平台)", width:340, sortable:true, dataIndex:'zone'},
            {header:"接入终端总数", width:85, sortable:true, dataIndex:'terminalsum'},
            {header:"车载终端", width:67, sortable:true, dataIndex:'vehiclesum'},
            {header:"手持终端", width:70, sortable:true, dataIndex:'phonesum'},
            {header:"笔记本终端", width:70, sortable:true, dataIndex:'notebooksum'},
            {header:"其他类型终端", width:85, sortable:true, dataIndex:'othersum'} ,
            {header:"中国移动接入", width:85, sortable:true, dataIndex:'mobile'} ,
            {header:"中国联通接入", width:85, sortable:true, dataIndex:'unicom'},
            {header:"中国电信接入", width:85, sortable:true, dataIndex:'telecom'}
        ],
        loader:new Ext.tree.TreeLoader({
            dataUrl:'../../TerminalMonitorAction_index.action',
            preloadChildren:true,
            uiProviders:{
                'col':Ext.tree.ColumnNodeUI
            }
        }),
        root:new Ext.tree.AsyncTreeNode({
            text:'all_id'
        })
    });

    new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[tree]
    });
    tree.render();

    tree.on('click', function (node, event) {
        if (node.leaf) {//如果是叶节点
            show_detail(node.attributes.idsystem);
        }

    });

})

function show_detail(idsystem) {
    var temp = idsystem;
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var start = 0;
    var pageSize = 10;
    var record = new Ext.data.Record.create([
        {name:'id', mapping:'id'},
        {name:'terminalName', mapping:'terminalName'},
        {name:'terminaltype', mapping:'terminaltype'},
        {name:'terminalOutlink', mapping:'terminalOutlink'},
        {name:'terminalos', mapping:'terminalos'},
        {name:'terminalband', mapping:'terminalband'},
        {name:'cardtype', mapping:'cardtype'},
        {name:'username', mapping:'username'},
        {name:'userid', mapping:'userid'},
        {name:'policenumber', mapping:'policenumber'},
        {name:'userdepart', mapping:'userdepart'},
        {name:'ifcancel', mapping:'ifcancel'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../TerminalMonitorAction_detail.action?idsystem=" + temp,
        params:{
            start:start, limit:pageSize
        }
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:'amount',
        root:'result'
    }, record);
    var store = new Ext.data.GroupingStore({
        proxy:proxy,
        reader:reader
    });
    store.load({
        params:{
            start:start, limit:pageSize
        }
    });
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"接入终端类型", dataIndex:"terminaltype", align:'center', sortable:true, menuDisabled:true, width:150, renderer:terminaltypeRender},
        {header:"接入终端外部链路", dataIndex:"terminalOutlink", align:'center', sortable:true, menuDisabled:true, width:180, renderer:outlinkRender},
//        {header:"终端操作系统",			dataIndex:"terminalos",  align:'center',sortable:true,menuDisabled:true,width:150,renderer:osRender},
//        {header:"接入终端品牌",			dataIndex:"terminalband",  align:'center',sortable:true,menuDisabled:true,width:150,renderer:bandRender},
        {header:"终端安全卡类型", dataIndex:"cardtype", align:'center', sortable:true, menuDisabled:true, width:160, renderer:cardtypeRender},
        {header:"所属用户", dataIndex:"username", align:'center', sortable:true, menuDisabled:true, width:140},
//        {header:"用户身份证",			dataIndex:"userid",  align:'center',sortable:true,menuDisabled:true,width:200},
        {header:"用户警号", dataIndex:"policenumber", align:'center', sortable:true, menuDisabled:true, width:140},
        {header:"所属单位", dataIndex:"userdepart", align:'center', sortable:true, menuDisabled:true, width:220},
        {header:"是否使用", dataIndex:"ifcancel", align:'center', sortable:true, menuDisabled:true, width:140, renderer:ifcancelRender},
        {header:'操作标记', dataIndex:'id', align:'center', sortable:true, menuDisabled:true, renderer:show_flag, width:140}

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"第{0}条到第{1}条，共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:getMonitorHeight()-60,
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colM,
        store:store,
        stripeRows:true,
        enableDragDrop:true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }),
        bbar:page_toolbar
    });

    var win = new Ext.Window({
        title:"查看终端监控详细信息",
        width:900,
        height:getMonitorHeight(),
        modal:true,
        draggable: false, // 禁止用户随意拖动窗口
        maximizable: true,  // 窗口最大化
        items:grid_panel,
        bbar:[
            '->',
            {
                text:'关闭',
                handler:function () {
                    win.close();
                }
            }
        ]
    }).show();
}
;
function getMonitorHeight() {
    return (document.body.clientHeight-8)/2
}

function cardtypeRender(value) {
    if (value == '001') {
        return 'USBKEY';
    }
    if (value == '002') {
        return 'TFCard';
    }
    else {
        return '其他类型';
    }
}

function outlinkRender(value) {
    if (value == '001') {
        return '中国移动';
    }
    if (value == '002') {
        return '中国连通';
    }
    if (value == '003') {
        return '中国电信';
    }
    else {
        return '其他';
    }

}

function osRender(value) {
    if (value == '001') {
        return 'Android';
    }
    if (value == '002') {
        return 'Windows Mobile';
    }
    if (value == '003') {
        return 'Windows Phone 7';
    }
    if (value == '004') {
        return 'Windows CE';
    }
    if (value == '005') {
        return 'Windows XP CE';
    }
    if (value == '006') {
        return 'Windows 2000';
    }
    if (value == '007') {
        return 'Windows 7';
    }
    if (value == '008') {
        return 'Ubuntu';
    }
    if (value == '009') {
        return 'Redhat';
    }
    if (value == '010') {
        return 'Fedora';
    }
    if (value == '011') {
        return 'CentOS';
    }
    if (value == '012') {
        return 'Symbian';
    }
    else {
        return '其他';
    }

}

function bandRender(value) {
    if (value == '001') {
        return '联想';
    }
    if (value == '002') {
        return '惠普';
    }
    if (value == '003') {
        return '华硕';
    }
    if (value == '004') {
        return '三星';
    }
    if (value == '005') {
        return '索尼';
    }
    if (value == '006') {
        return '戴尔';
    }
    if (value == '007') {
        return '东芝';
    }
    if (value == '008') {
        return '明基';
    }
    if (value == '009') {
        return '神舟';
    }
    if (value == '010') {
        return '海尔';
    }
    if (value == '011') {
        return '清华同方';
    }
    if (value == '012') {
        return '方正';
    }
    if (value == '013') {
        return '苹果';
    }
    if (value == '014') {
        return '富士通';
    }
    if (value == '015') {
        return '诺基亚';
    }
    if (value == '016') {
        return 'HTC';
    }
    if (value == '017') {
        return '摩托罗拉';
    }
    if (value == '018') {
        return '索尼爱立信';
    }
    if (value == '019') {
        return '黑莓';
    }
    if (value == '020') {
        return '天语';
    }
    if (value == '021') {
        return 'LG';
    }
    if (value == '022') {
        return '酷派';
    }
    if (value == '023') {
        return '魅族';
    }
    if (value == '024') {
        return '步步高';
    }
    if (value == '025') {
        return '华为';
    }
    if (value == '026') {
        return '中兴';
    }
    if (value == '027') {
        return '爱国者';
    }
    else {
        return '其他';
    }
}

function terminaltypeRender(value) {
    if (value == '001') {
        return '手持设备';
    }
    if (value == '002') {
        return '车载专用设备';
    }
    if (value == '003') {
        return '笔记本';
    }
    else {
        return '其它类型';
    }
}


function ifcancelRender(value) {
    if (value == '0') {
        return '正常使用';
    }
    if (value == '1') {
        return '已经注销';
    }

}
function show_flag(value) {
    return "<a href='javascript:;' onclick='detail()'>详细</a>&nbsp;&nbsp;"
//          +"<a  style='color:blue;'onclick='cut()'>截屏</a>"
        ;
}

function cut() {

}
function detail() {
    var grid = Ext.getCmp('grid.info');
    var store = grid.getStore();
    var selModel = grid.getSelectionModel();
    var formPanel;
    if (selModel.hasSelection()) {
        var selections = selModel.getSelections();
        Ext.each(selections, function (item) {
            formPanel = new Ext.form.FormPanel({
                frame:true,
                border:false,
                autoScroll:true,
                items:[
                    {
                        plain:true,
                        labelAlign:'right',
                        labelWidth:150,
                        border:false,
                        layout:'form',
                        autoScroll:true,
                        defaultType:'displayfield',
                        defaults:{
                            width:150,
                            allowBlank:false,
                            blankText:'该项不能为空！'
                        },
                        items:[
                            {
                                fieldLabel:"接入终端类型",
                                value:terminaltypeRender(item.data.terminaltype)
                            },
                            {
                                fieldLabel:"接入终端外部链路",
                                value:outlinkRender(item.data.terminalOutlink)
                            },
                            {
                                fieldLabel:"接入终端操作系统",
                                value:osRender(item.data.terminalos)
                            },
                            {
                                fieldLabel:"接入终端品牌",
                                value:bandRender(item.data.terminalband)
                            },
                            {
                                fieldLabel:"接入终端安全卡类型",
                                value:cardtypeRender(item.data.terminalband)
                            },
                            {
                                fieldLabel:"所属用户",
                                value:item.data.username
                            },
                            {
                                fieldLabel:"用户身份证",
                                value:item.data.userid
                            },
                            {
                                fieldLabel:"用户警号",
                                value:item.data.policenumber
                            },
                            {
                                fieldLabel:"所属单位",
                                value:item.data.userdepart
                            },
                            {
                                fieldLabel:"是否正常使用",
                                value:ifcancelRender(item.data.ifcancel)
                            }
                        ]
                    }
                ]
            });
        });
    }
    var win = new Ext.Window({
        title:"终端详细信息",
        width:450,
        layout:'fit',
        height:350,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                text:'关闭',
                handler:function () {
                    win.close();
                }
            }
        ]
    }).show();
}






