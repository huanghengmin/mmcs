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
//     title: '���м��',
        columns:[
            {id:'zone', header:"����(���м��ƽ̨)", width:340, sortable:true, dataIndex:'zone'},
            {header:"�����ն�����", width:85, sortable:true, dataIndex:'terminalsum'},
            {header:"�����ն�", width:67, sortable:true, dataIndex:'vehiclesum'},
            {header:"�ֳ��ն�", width:70, sortable:true, dataIndex:'phonesum'},
            {header:"�ʼǱ��ն�", width:70, sortable:true, dataIndex:'notebooksum'},
            {header:"���������ն�", width:85, sortable:true, dataIndex:'othersum'} ,
            {header:"�й��ƶ�����", width:85, sortable:true, dataIndex:'mobile'} ,
            {header:"�й���ͨ����", width:85, sortable:true, dataIndex:'unicom'},
            {header:"�й����Ž���", width:85, sortable:true, dataIndex:'telecom'}
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
        if (node.leaf) {//�����Ҷ�ڵ�
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
    var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var colM = new Ext.grid.ColumnModel([
        rowNumber,
//            {header:"id",			dataIndex:"id",  align:'center',sortable:true,menuDisabled:true,width:100},
        {header:"�����ն�����", dataIndex:"terminaltype", align:'center', sortable:true, menuDisabled:true, width:150, renderer:terminaltypeRender},
        {header:"�����ն��ⲿ��·", dataIndex:"terminalOutlink", align:'center', sortable:true, menuDisabled:true, width:180, renderer:outlinkRender},
//        {header:"�ն˲���ϵͳ",			dataIndex:"terminalos",  align:'center',sortable:true,menuDisabled:true,width:150,renderer:osRender},
//        {header:"�����ն�Ʒ��",			dataIndex:"terminalband",  align:'center',sortable:true,menuDisabled:true,width:150,renderer:bandRender},
        {header:"�ն˰�ȫ������", dataIndex:"cardtype", align:'center', sortable:true, menuDisabled:true, width:160, renderer:cardtypeRender},
        {header:"�����û�", dataIndex:"username", align:'center', sortable:true, menuDisabled:true, width:140},
//        {header:"�û����֤",			dataIndex:"userid",  align:'center',sortable:true,menuDisabled:true,width:200},
        {header:"�û�����", dataIndex:"policenumber", align:'center', sortable:true, menuDisabled:true, width:140},
        {header:"������λ", dataIndex:"userdepart", align:'center', sortable:true, menuDisabled:true, width:220},
        {header:"�Ƿ�ʹ��", dataIndex:"ifcancel", align:'center', sortable:true, menuDisabled:true, width:140, renderer:ifcancelRender},
        {header:'�������', dataIndex:'id', align:'center', sortable:true, menuDisabled:true, renderer:show_flag, width:140}

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"��{0}������{1}������{2}��",
        emptyMsg:"û�м�¼",
        beforePageText:"��ǰҳ",
        afterPageText:"��{0}ҳ"
    });
    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:getMonitorHeight()-60,
        animCollapse:true,
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colM,
        store:store,
        stripeRows:true,
        enableDragDrop:true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }),
        bbar:page_toolbar
    });

    var win = new Ext.Window({
        title:"�鿴�ն˼����ϸ��Ϣ",
        width:900,
        height:getMonitorHeight(),
        modal:true,
        draggable: false, // ��ֹ�û������϶�����
        maximizable: true,  // �������
        items:grid_panel,
        bbar:[
            '->',
            {
                text:'�ر�',
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
        return '��������';
    }
}

function outlinkRender(value) {
    if (value == '001') {
        return '�й��ƶ�';
    }
    if (value == '002') {
        return '�й���ͨ';
    }
    if (value == '003') {
        return '�й�����';
    }
    else {
        return '����';
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
        return '����';
    }

}

function bandRender(value) {
    if (value == '001') {
        return '����';
    }
    if (value == '002') {
        return '����';
    }
    if (value == '003') {
        return '��˶';
    }
    if (value == '004') {
        return '����';
    }
    if (value == '005') {
        return '����';
    }
    if (value == '006') {
        return '����';
    }
    if (value == '007') {
        return '��֥';
    }
    if (value == '008') {
        return '����';
    }
    if (value == '009') {
        return '����';
    }
    if (value == '010') {
        return '����';
    }
    if (value == '011') {
        return '�廪ͬ��';
    }
    if (value == '012') {
        return '����';
    }
    if (value == '013') {
        return 'ƻ��';
    }
    if (value == '014') {
        return '��ʿͨ';
    }
    if (value == '015') {
        return 'ŵ����';
    }
    if (value == '016') {
        return 'HTC';
    }
    if (value == '017') {
        return 'Ħ������';
    }
    if (value == '018') {
        return '���ᰮ����';
    }
    if (value == '019') {
        return '��ݮ';
    }
    if (value == '020') {
        return '����';
    }
    if (value == '021') {
        return 'LG';
    }
    if (value == '022') {
        return '����';
    }
    if (value == '023') {
        return '����';
    }
    if (value == '024') {
        return '������';
    }
    if (value == '025') {
        return '��Ϊ';
    }
    if (value == '026') {
        return '����';
    }
    if (value == '027') {
        return '������';
    }
    else {
        return '����';
    }
}

function terminaltypeRender(value) {
    if (value == '001') {
        return '�ֳ��豸';
    }
    if (value == '002') {
        return '����ר���豸';
    }
    if (value == '003') {
        return '�ʼǱ�';
    }
    else {
        return '��������';
    }
}


function ifcancelRender(value) {
    if (value == '0') {
        return '����ʹ��';
    }
    if (value == '1') {
        return '�Ѿ�ע��';
    }

}
function show_flag(value) {
    return "<a href='javascript:;' onclick='detail()'>��ϸ</a>&nbsp;&nbsp;"
//          +"<a  style='color:blue;'onclick='cut()'>����</a>"
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
                            blankText:'�����Ϊ�գ�'
                        },
                        items:[
                            {
                                fieldLabel:"�����ն�����",
                                value:terminaltypeRender(item.data.terminaltype)
                            },
                            {
                                fieldLabel:"�����ն��ⲿ��·",
                                value:outlinkRender(item.data.terminalOutlink)
                            },
                            {
                                fieldLabel:"�����ն˲���ϵͳ",
                                value:osRender(item.data.terminalos)
                            },
                            {
                                fieldLabel:"�����ն�Ʒ��",
                                value:bandRender(item.data.terminalband)
                            },
                            {
                                fieldLabel:"�����ն˰�ȫ������",
                                value:cardtypeRender(item.data.terminalband)
                            },
                            {
                                fieldLabel:"�����û�",
                                value:item.data.username
                            },
                            {
                                fieldLabel:"�û����֤",
                                value:item.data.userid
                            },
                            {
                                fieldLabel:"�û�����",
                                value:item.data.policenumber
                            },
                            {
                                fieldLabel:"������λ",
                                value:item.data.userdepart
                            },
                            {
                                fieldLabel:"�Ƿ�����ʹ��",
                                value:ifcancelRender(item.data.ifcancel)
                            }
                        ]
                    }
                ]
            });
        });
    }
    var win = new Ext.Window({
        title:"�ն���ϸ��Ϣ",
        width:450,
        layout:'fit',
        height:350,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                text:'�ر�',
                handler:function () {
                    win.close();
                }
            }
        ]
    }).show();
}






