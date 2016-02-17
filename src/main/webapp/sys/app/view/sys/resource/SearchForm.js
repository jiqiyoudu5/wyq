Ext.define('EJ.view.sys.resource.SearchForm', {
			alias : 'widget.searchForm',
			extend : 'Ext.form.Panel',
			id : 'SearchForm',
			border : false,
			initComponent : function() {
				this.items = [{
							xtype : 'container',
							anchor : '100%',
							layout : 'hbox',
							items : [{
										xtype : 'container',
										layout : 'anchor',
										width : 265,
										items : [{
													xtype : 'textfield',
													fieldLabel : '关键词检索',
													anchor : '98%',
													emptyText : '资源名称',
													labelWidth : 70,
													name : 'name'
												}]
									}, {
										xtype : 'container',
										flex : 1,
										width : 35,
										items : [{
													text : '搜',
													xtype : 'button',
													action : 'search-by-resourceName'
												}]
									}]
						}];
				this.callParent(arguments);
			}
		});