Ext.define('EJ.view.sys.chart.FindChartByTypeForm', {
			extend : 'Ext.form.Panel',
			alias : 'widget.findChartByTypeForm',
			bodyPadding : '20 5 0',
			width : 200,
			border : false,
			initComponent : function() {
				this.items = [{
							xtype : 'fieldset',
							collapsible : true,
							collapsed : true,
							title : '选择统计内容',
							margin : '5',
							flex : 1,
							items : [{
										xtype : 'combo',
										valueField : 'chartType',
										editable : false,
										store : ['用户统计', '招投标信息统计'],
										name : 'chartType',
										value : '用户统计',
										labelWidth : 60,
										anchor : '95%'
									}]
						}];

				this.callOverridden(arguments);
			}
		});