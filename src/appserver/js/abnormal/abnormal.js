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
//        title: '违规通报',
        columns:[{
            header:'地区(集中监控平台)',
            width:365,
            dataIndex:'zonename'
        }/*,{
            header:'地区编号',
            width:90,
            hidden:true,
            dataIndex:'zonecode'
        }*/,{
            header:'本月违规总数',
            width:104,
            dataIndex:'mNum'
        },{
            header:'本月已处理违规数',
            width:104,
            dataIndex:'mtreadNum'
        },{
            header:'本月未处理违规数',
            width:104,
            dataIndex:'mNtreadNum'
        },{
            header:'本年违规总数',
            width:104,
            dataIndex:'yNum'
        },{
            header:'本年已处理违规数',
            width:104,
            dataIndex:'ytreadNum'
        },{
            header:'本年未处理违规数',
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
        if(node.leaf){//如果是叶节点
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
            header : "违规事件标识",
            dataIndex : 'idalertmatter',
            width:90
        },*/ {
            header : "违规事件类型",
            dataIndex : 'abnormaltype',
            width:90
        }, {
            header : "违规对象类型",
            dataIndex : 'abnormalobject',
            width:90
        }, {
            header : "违规事件描述",
            dataIndex : 'exceptiondesc',
            width:90
        }, {
            header : "违规事件发生时间",
            dataIndex : 'happentime',
            width:134
        }, {
            header : "违规事件处理时间",
            dataIndex : 'treattime',
            width:134
        }, {
            header : "违规处理结果",
            dataIndex : 'treadresult',
            width:90
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxabbutton,
            width:90
        }
    ]);
    abcm.defaultSortable = true;
    function xxabbutton() {
        var returnStr = "<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>详细</a>";
        return returnStr;

    }
    var abds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../SysabnormalAction_findAbnormaVos.action'
        }),//调用的动作
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
        }//列的映射
        ])
    });
//    abds.load();

    var abgrid;
    function createLookabgrid() {
        abgrid = new Ext.grid.GridPanel({

            id : 'resourcegrid',
//            title : '违规通报明细列表',
            border:false,
            store : abds,
            cm : abcm,
            renderTo :Ext.getBody(), /*'noteDiv',*/
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : abds,
                displayInfo:true,
                displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
                emptyMsg:"没有记录",
                beforePageText:"当前页",
                afterPageText:"共{0}页"
            }),
            viewConfig:{
//                autoFill:true
                //forceFit:true
            },
            tbar : ["时间:",
                new Ext.form.ComboBox({
                hiddenName:"timetype",
                id:'timetype1',
                fieldLabel:"消息类型",
//                emptyText: '请选择消息类型',
                store : new Ext.data.SimpleStore({fields:['value','name'],data:[ ["0",'本年'], ["1",'本月']]}),
                valueField : "value",
                displayField : "name",
                typeAhead : true,
                mode : "local",
                forceSelection : true,
                triggerAction : "all",
                width:80,
                OnFocus : true
            }),"处理结果:",
                new Ext.form.ComboBox({
                hiddenName:"treadresult",
                id:'treadresult1',
                fieldLabel:"处理结果",
//                emptyText: '请选择消息类型',
                store : new Ext.data.SimpleStore({fields:['value','name'],data:[ ["001",'已处理'], ["002",'待处理'], ["003",'未处理'], ["004",'处理中'], ["005",'其它结果'], ["%",'所有']]}),
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
                text : ' 搜索  ',
                tooltip : '按条件搜索',
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
            loadMask : true,// 载入遮罩动画
            autoShow : true
        });
    }
    function winlookabn(abgridd) {
        var abgrid = abgridd;
        var abgwin = new Ext.Window({
            title : '违规通报明细列表',
            width : "800",
//            height :490,
            items: [abgrid],
            bbar : ["->",
                {
                    text:'关闭',
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
                    fieldLabel : '违规事件标识' ,
                    name : 'idalertmatter',
                    id:'idalertmatter',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规事件类型' ,
                    name : 'abnormaltype',
                    id: 'abnormaltype',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规对象类型' ,
                    name : 'abnormalobject',
                    id: 'abnormalobject',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规事件描述' ,
                    name : 'exceptiondesc',
                    id: 'exceptiondesc',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规事件发生时间' ,
                    name : 'happentime',
                    id: 'happentime',
                    width:193,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规事件处理时间' ,
                    name : 'treattime',
                    id: 'treattime',
                    width:193,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '违规处理结果' ,
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
            title : '查看违规详细详细',
            width : 390,
//            height :490,
            items: [myform],
            bbar : ["->",
                {
                    text:'关闭',
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


