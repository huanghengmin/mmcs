Ext.onReady(function() {
    document.body.innerHTML="<div id='ctree'></div>";
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var tree = new Ext.tree.ColumnTree({
        el:'ctree',
        loadMask : true,
        rootVisible:false,
        autoScroll:true,
        expandable:false,
        enableDD:true,
        animate:true,
        border : false,
//        title: 'Υ��ͨ��',
        columns:[{
            header:'����(���м��ƽ̨)',
            width:365,
            dataIndex:'zonename'
        }/*,{
            header:'�������',
            width:90,
            hidden:true,
            dataIndex:'zonecode'
        }*/,{
            header:'����Υ������',
            width:104,
            dataIndex:'mNum'
        },{
            header:'�����Ѵ���Υ����',
            width:104,
            dataIndex:'mtreadNum'
        },{
            header:'����δ����Υ����',
            width:104,
            dataIndex:'mNtreadNum'
        },{
            header:'����Υ������',
            width:104,
            dataIndex:'yNum'
        },{
            header:'�����Ѵ���Υ����',
            width:104,
            dataIndex:'ytreadNum'
        },{
            header:'����δ����Υ����',
            width:104,
            dataIndex:'yNtreadNum'
        }],
        loader: new Ext.tree.TreeLoader({
//            dataUrl :'../../js/abnormal/column-data3.js',
            dataUrl :'../../SysabnormalAction_findAbnormaNum.action',
            preloadChildren: true,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }) ,
        root: new Ext.tree.AsyncTreeNode({
            text:'all_id'
        })
    });
//    tree.expandAll();
    tree.render();

    tree.on('click', function(node, event){
        if(node.leaf){//�����Ҷ�ڵ�
//            alert(node.attributes.zonecode);
            Ext.Ajax.request({
                url:'../../SysabnormalAction_execute.action',

                params:{idsystem:node.attributes.zonecode}
            });
            lookabmodel();
        }

    });



    var abcsm = new Ext.grid.CheckboxSelectionModel({
    });
    var rowNumber = new Ext.grid.RowNumberer();
    var abcm = new Ext.grid.ColumnModel([
//        abcsm,
        rowNumber,
        /*{
            header : 'ID',
            dataIndex : 'id',
            hidden:true,
            width:15
        }, {
            header : "Υ���¼���ʶ",
            dataIndex : 'idalertmatter',
            width:90
        },*/ {
            header : "Υ���¼�����",
            dataIndex : 'abnormaltype',
            width:90
        }, {
            header : "Υ���������",
            dataIndex : 'abnormalobject',
            width:90
        }, {
            header : "Υ���¼�����",
            dataIndex : 'exceptiondesc',
            width:90
        }, {
            header : "Υ���¼�����ʱ��",
            dataIndex : 'happentime',
            width:134
        }, {
            header : "Υ���¼�����ʱ��",
            dataIndex : 'treattime',
            width:134
        }, {
            header : "Υ�洦����",
            dataIndex : 'treadresult',
            width:90
        },{
            header:"����",
            dataIndex:"button",
            renderer:xxabbutton,
            width:90
        }
    ]);
    abcm.defaultSortable = true;
    function xxabbutton() {
        var returnStr = "<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>��ϸ</a>";
        return returnStr;

    }
    var abds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../SysabnormalAction_findAbnormaVos.action'
        }),//���õĶ���
        reader : new Ext.data.JsonReader({
            totalProperty : 'ablist',
            root : 'abrow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'idalertmatter',
            mapping : 'idalertmatter',
            type : 'string'
        }, {
            name : 'abnormaltype',
            mapping : 'abnormaltype',
            type : 'string'
        }, {
            name : 'abnormalobject',
            mapping : 'abnormalobject',
            type : 'string'
        }, {
            name : 'exceptiondesc',
            mapping : 'exceptiondesc',
            type : 'string'
        }, {
            name : 'happentime',
            mapping : 'happentime',
            type : 'string'
        }, {
            name : 'treattime',
            mapping : 'treattime',
            type : 'string'
        }, {
            name : 'treadresult',
            mapping : 'treadresult',
            type : 'string'
        }//�е�ӳ��
        ])
    });
//    abds.load();

    var abgrid;
    function createLookabgrid() {
        abgrid = new Ext.grid.GridPanel({

            id : 'resourcegrid',
//            title : 'Υ��ͨ����ϸ�б�',
            border:false,
            store : abds,
            cm : abcm,
            renderTo :Ext.getBody(), /*'noteDiv',*/
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : abds,
                displayInfo:true,
                displayMsg:"��ʾ��{0}����¼����{1}����¼��һ��{2}��",
                emptyMsg:"û�м�¼",
                beforePageText:"��ǰҳ",
                afterPageText:"��{0}ҳ"
            }),
            viewConfig:{
//                autoFill:true
                //forceFit:true
            },
            tbar : ["ʱ��:",
                new Ext.form.ComboBox({
                hiddenName:"timetype",
                id:'timetype1',
                fieldLabel:"��Ϣ����",
//                emptyText: '��ѡ����Ϣ����',
                store : new Ext.data.SimpleStore({fields:['value','name'],data:[ ["0",'����'], ["1",'����']]}),
                valueField : "value",
                displayField : "name",
                typeAhead : true,
                mode : "local",
                forceSelection : true,
                triggerAction : "all",
                width:80,
                OnFocus : true
            }),"������:",
                new Ext.form.ComboBox({
                hiddenName:"treadresult",
                id:'treadresult1',
                fieldLabel:"������",
//                emptyText: '��ѡ����Ϣ����',
                store : new Ext.data.SimpleStore({fields:['value','name'],data:[ ["001",'�Ѵ���'], ["002",'������'], ["003",'δ����'], ["004",'������'], ["005",'�������'], ["%",'����']]}),
                valueField : "value",
                displayField : "name",
                typeAhead : true,
                mode : "local",
                forceSelection : true,
                triggerAction : "all",
                width:80,
                OnFocus : true
            }),{
                id : 'ss',
                text : ' ����  ',
                tooltip : '����������',
                iconCls : 'add',
                handler : function() {
                    var timetype =  Ext.getCmp("timetype1").getValue();
                    var treadresult = Ext.getCmp("treadresult1").getValue();
//                    alert(timetype+"----------------"+treadresult);
                    Ext.Ajax.request({
                        url:'../../SysabnormalAction_execute.action',
                        params:{timetype:timetype, treadresult:treadresult}
                    });
                    abgrid.render();
                    abds.proxy = new Ext.data.HttpProxy({url:'../../SysabnormalAction_findAbnormaVosByTimeAndTreadresult.action'});
                    abds.load({params:{start:0,limit:10}});
                }
            }],

            width : "780",
            height :"240",
//            frame : true,
            loadMask : true,// �������ֶ���
            autoShow : true
        });
    }
    function winlookabn(abgridd) {
        var abgrid = abgridd;
        var abgwin = new Ext.Window({
            title : 'Υ��ͨ����ϸ�б�',
            width : "800",
//            height :490,
            items: [abgrid],
            bbar : ["->",
                {
                    text:'�ر�',
                    handler:function() {
                        abgwin.close();
                    }
                }
            ]
        });
        abgwin.show();
    }
    Model.lookabn = function lookabn(){
        abds.load({params:{start:0,limit:10}});
        createLookabgrid();
        winlookabn(abgrid);
    }

    var myform;
    function createLookform() {
        myform = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            baseParams : {create : true },
            //  labelWidth : 70 ,
            items : [
                {
                    fieldLabel : 'Υ���¼���ʶ' ,
                    name : 'idalertmatter',
                    id:'idalertmatter',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ���¼�����' ,
                    name : 'abnormaltype',
                    id: 'abnormaltype',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ���������' ,
                    name : 'abnormalobject',
                    id: 'abnormalobject',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ���¼�����' ,
                    name : 'exceptiondesc',
                    id: 'exceptiondesc',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ���¼�����ʱ��' ,
                    name : 'happentime',
                    id: 'happentime',
                    width:193,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ���¼�����ʱ��' ,
                    name : 'treattime',
                    id: 'treattime',
                    width:193,
                    xtype: 'displayfield'
                },{
                    fieldLabel : 'Υ�洦����' ,
                    name : 'treadresult',
                    id: 'treadresult',
                    width:163,
                    xtype: 'displayfield'
                }
            ]
        });
    }
    Model.lookxx = function lookxx(){
        createLookform();
        Ext.getCmp("idalertmatter").setValue(abgrid.getSelectionModel().getSelections()[0].get("idalertmatter"));
        Ext.getCmp("abnormaltype").setValue(abgrid.getSelectionModel().getSelections()[0].get("abnormaltype"));
        Ext.getCmp("abnormalobject").setValue(abgrid.getSelectionModel().getSelections()[0].get("abnormalobject"));
        Ext.getCmp("exceptiondesc").setValue(abgrid.getSelectionModel().getSelections()[0].get("exceptiondesc"));
        Ext.getCmp("happentime").setValue(abgrid.getSelectionModel().getSelections()[0].get("happentime"));
        Ext.getCmp("treattime").setValue(abgrid.getSelectionModel().getSelections()[0].get("treattime"));
        Ext.getCmp("treadresult").setValue(abgrid.getSelectionModel().getSelections()[0].get("treadresult"));
        winlookxx(myform);
    }
    function winlookxx(form) {
        var myform = form;
        var lxxwin = new Ext.Window({
            title : '�鿴Υ����ϸ��ϸ',
            width : 390,
//            height :490,
            items: [myform],
            bbar : ["->",
                {
                    text:'�ر�',
                    handler:function() {
                        lxxwin.close();
                    }
                }
            ]
        });
        lxxwin.show();
    }

});

var Model = new Object;
function lookabmodel(){
    Model.lookabn();
}

function lookmodel(){
    Model.lookxx();
}

function setHeight(){
    var h = document.body.clientHeight-110;
    return h;
}

function setWidth(){
    return document.body.clientWidth+15;
}


