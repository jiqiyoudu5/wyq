Ext.define('EJ.store.sys.chart.UserChartStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.chart.UserChartModel',
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : 'chart/finduserschart',
				reader : {
					type : 'json',
					root : 'data',
					successProperty : 'success'
				}
			}
		});