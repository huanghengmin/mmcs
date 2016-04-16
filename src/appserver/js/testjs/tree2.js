Ext.onReady(function(){
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
     title: '运行监控',
     columns:[{
             header:'所有ID',
             width:200,
             dataIndex:'all_id'
         },{
             header:'审核日志标题',
             width:200,
             dataIndex:'shrz_title'
         },{
             header:'审核日志内容',
             width:400,
             dataIndex:'shrz_content'
         },{
             header:'提问者',
             width:80,
             dataIndex:'asker'
         },{
             header:'回复人',
             width:80,
             dataIndex:'replier'
         },{
             header:'创建时间',
             width:150,
             dataIndex:'shrz_createtime'
         }],
         loader: new Ext.tree.TreeLoader({
             dataUrl :'column-data3.js',
//             dataUrl :'ColumtreeAction_selectall.action',
             preloadChildren: true,
             uiProviders:{
                 'col': Ext.tree.ColumnNodeUI
             }
         }) ,
       root: new Ext.tree.AsyncTreeNode({
            text:'all_id'
        })
    });
    tree.expandAll();
    tree.render();
})


