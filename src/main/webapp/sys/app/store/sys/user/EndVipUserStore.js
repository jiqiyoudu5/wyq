Ext.define('EJ.store.sys.user.EndVipUserStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.user.UserModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : 'user/findendvipusers',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});