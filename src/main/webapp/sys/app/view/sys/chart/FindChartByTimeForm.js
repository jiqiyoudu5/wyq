Ext.define('EJ.view.sys.chart.FindChartByTimeForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.findChartByTimeForm',
	border : false,
	initComponent : function() {
		var date = new Date();
		var year = date.getFullYear();
		var month = ((date.getMonth() + 1) >= 10) ? (date.getMonth() + 1)
				.toString() : '0' + (date.getMonth() + 1).toString();
		this.items = [{
			xtype : 'fieldset',
			flex : 1,
			title : '选择统计时间',
			items : [{
				xtype : 'combobox',
				fieldLabel : '年',
				valueField : 'year',
				editable : false,
				store : ['全部', '2013', '2014', '2015', '2016', '2017', '2018',
						'2019', '2020', '2021', '2022', '2023', '2024', '2025'],
				name : 'year',
				value : year,
				labelWidth : 30,
				anchor : '95%'
			}, {
				xtype : 'combo',
				fieldLabel : '月',
				valueField : 'month',
				editable : false,
				value : month,
				store : ['全年', '01', '02', '03', '04', '05', '06', '07', '08',
						'09', '10', '11', '12'],
				name : 'month',
				labelWidth : 30,
				anchor : '95%'
			}, {
				border : false,
				bodyPadding : '10 60 10',
				flex : 1,
				items : [{
							xtype : 'button',
							text : '开始统计',
							action : 'submitSelectTime'

						}]
			}]
		}];

		this.callOverridden(arguments);
	}
});