Ext.define('EJ.store.sys.user.ExpireVipUserStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.user.UserModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : 'user/findexpirevipusers',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});