Ext.define('EJ.view.sys.chart.UserChart', {
			alias : 'widget.userChart',
			extend : 'Ext.chart.Chart',
			renderTo : Ext.getBody(),
			store : 'sys.chart.UserChartStore',
			animate : true,// 配置动画效果
			shadow : true,// 阴影效果
			legend : {
				position : 'right'// 图例位置
			},
			axes : [{
						title : 'UserCount',
						type : 'Numeric',
						position : 'left',
						grid : true,
						fields : ['count', 'vipCount', 'commonCount'],
						minimum : 0,
						maximum : 100
					}, {
						title : 'Date',
						type : 'Category',
						position : 'bottom',
						fields : ['date']
					}],
			series : [{
				type : 'column',
				axis : 'left',
				highlight : true,// 鼠标放在线或柱上的效果
				title : '系统所有用户',
				tips : {
					trackMouse : true,
					width : 140,
					height : 60,
					renderer : function(storeItem, item) {
						this.setTitle(storeItem.get('date') + ' :</p> 注册用户 '
								+ storeItem.get('count') + ' 人');
					}
				},
				label : {
					display : 'outside',// insideEnd-->>标签数值显示在柱表面
					'text-anchor' : 'middle',
					field : 'count',
					renderer : Ext.util.Format.numberRenderer('0'),
					orientation : 'vertical',
					color : '#333'
				},
				xField : 'date',
				yField : 'count'
			}, {
				type : 'line',
				axis : 'left',
				xField : 'date',
				yField : 'vipCount',
				title : 'vip会员',
				highlight : true,
				markerConfig : {
					type : 'circle',
					size : 4,
					radius : 4,
					'stroke-width' : 0
				},
				style : {
					// fill : '#18428E',
					// stroke : '#18428E',
					fill : '#38B8BF',// 折线点颜色
					stroke : '#38B8BF',// 折线颜色
					'stroke-width' : 3
				},
				tips : {
					trackMouse : true,
					width : 140,
					height : 60,
					renderer : function(storeItem, item) {
						this
								.setTitle(storeItem.get('date')
										+ '  :</p> <font color=#38B8BF>注册VIP</font> '
										+ storeItem.get('vipCount') + ' 人');
					}
				}
			}, {
				type : 'line',
				highlight : {
					size : 7,
					radius : 7
				},
				axis : 'left',
				title : '普通用户',
				smooth : true,
				xField : 'date',
				yField : 'commonCount',
				markerConfig : {
					type : 'cross',
					size : 4,
					radius : 4,
					'stroke-width' : 0
				},
				style : {
					// fill : '#18428E',
					// stroke : '#18428E',
					fill : '#f9178c',// 折线点颜色#fbea89
					stroke : '#f18aed',// 折线颜色
					'stroke-width' : 3
				},
				tips : {
					trackMouse : true,
					width : 140,
					height : 50,
					renderer : function(storeItem, item) {
						this
								.setTitle(storeItem.get('date')
										+ ' 日  :</br> <font color=#f9178c>注册普通用户</font> '
										+ storeItem.get('vipCount') + ' 人');
					}
				}
			}]
		});
