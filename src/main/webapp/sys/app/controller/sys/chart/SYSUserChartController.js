Ext.define('EJ.controller.sys.chart.SYSUserChartController', {
			extend : 'Ext.app.Controller',
			init : function() {
				this.control({
							'findChartByTypeForm combo[name=chartType]' : {
								blur : this.chartType
							},
							'button[action=submitSelectTime]' : {
								click : this.findChartByTime
							}
						});
			},
			chartType : function(btn) {// 选择要统计的类型(对象)
				alert(btn.getValue());
			},
			findChartByTime : function(btn) {// 按所选时间统计
				var form = btn.up('form').getForm().getValues();
				var store = this.getStore('sys.chart.UserChartStore');
				store.proxy.url = 'chart/finduserschart';
				store.proxy.extraParams = form;
				store.load();
			}
		});