
Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.chart.Chart.CHART_URL = '../../ext/resources/charts.swf';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var buildStore = new Ext.data.Store({
        url:'../../AnalysisAction_selectBuilder.action',
        reader:new Ext.data.JsonReader({
                totalProperty:'total',
                root:'rows'
            },new Ext.data.Record.create([
            {name:'builder',mapping:'builder'},
            {name:'count',mapping:'count'}
        ])
        )
    });
    buildStore.load();
    var formPanel = new Ext.Panel({
//        frame:true,
        autoScroll:true,
        border:false,
        layout: 'form',
        items:[{
            xtype : 'piechart',
            store : buildStore,
            dataField:'count',
            categoryField:'builder',
            series:[{
                style:{
                    colors:["#CDCFFF", "#312FCE","#0CE9A","#FECA65"]
                }
            }],
            extraStyle:{
                legend:{
                    display:'top',
                    padding:5,
                    font:{
                        family: 'Tahoma',
                        size: 13
                    }
                }
            }
        }]
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[formPanel]
    });


});
function setHeight(){
    var h = document.body.clientHeight-8;
    return h;
}

function setWidth(){
    return document.body.clientWidth-8;
}


