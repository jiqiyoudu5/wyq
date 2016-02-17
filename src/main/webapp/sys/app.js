/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by Sencha
 * Cmd when upgrading.
 */

Ext.application({
			name : 'EJ',

			//extend : 'EJ.Application',

			autoCreateViewport : true,

			views : [
			'sys.Main', 
			'sys.Banner', 
			'sys.SYSMenuTree', 
			'sys.SYSHome',
			'sys.chart.UserChart',
			'sys.chart.FindChartByTimeForm',
			'sys.user.UserList', 
			'sys.user.UserInfoWin',
			'sys.user.RegistUserWin', 
			'sys.user.RegistUserForm',
			'sys.user.SearchUserWin', 
			'sys.user.ModifcationUserWin',
			'sys.user.ModifcationUserForm', 
			'sys.user.ModifcationUserPassword',
			'sys.user.ExpireVipUserList',
			'sys.user.EndVipUserList',
			'sys.user.RemoveUserRolesWin',
			'sys.roles.SYSRoleMain', 
			'sys.roles.SYSRoleList',
			'sys.roles.AddWin', 
			'sys.roles.RoleInfoWin',
			'sys.roles.EditRoleWin', 
			'sys.roles.SYSRoleResourcesTab',
			'sys.roles.SYSRoleUsersTab', 
			'sys.roles.SYSRoleMenuTab',
			'sys.roles.SYSRoleResourcesConfigWin',
			'sys.roles.SYSRoleUsersConfigWin',
			'sys.resource.SYSResourceList', 
			'sys.resource.EditResWin',
			'sys.resource.AddWin', 
			'sys.resource.SearchForm',
			'sys.menu.SYSTreeList', 
			'sys.menu.SYSAddMenu',
			'sys.menu.SYSEditMenu',
			'sys.dictionary.SYSDictionaryMain',
			'sys.dictionary.SYSDicTreeList', 
			'sys.dictionary.AddDicTypeWin',
			'sys.dictionary.UpdateDicTypeWin', 
			'sys.dictionary.SYSDicList',
			'sys.dictionary.AddDicWin', 
			'sys.dictionary.UpdateDicWin',
			'sys.files.Manager', 
			'sys.files.List',
			'sys.files.UploadWindow',
			'sys.database.BackManager'
			],

			controllers : [
			'sys.mainController',
			'sys.chart.SYSUserChartController',
			'sys.user.UserController',
			'sys.roles.SYSRoleController',
			'sys.resource.SYSResourceController',
			'sys.menu.SYSMenuController',
			'sys.dictionary.SYSDictionaryController',
			'sys.file.SYSFilesController'
			],

			stores : [
			'sys.SYSMenuTreeStore',
			'sys.chart.UserChartStore',
			'sys.user.UserStore', 
			'sys.user.ExpireVipUserStore',
			'sys.user.EndVipUserStore', 
			'sys.roles.SYSUserRoleStore',
			'sys.roles.SYSRoleStore', 
			'sys.roles.SYSRoleResourceStore',
			'sys.roles.SYSRoleUserStore', 
			'sys.roles.SYSRoleNoUserStore',
			'sys.roles.SYSRoleNoResourceStore',
			'sys.resource.SYSResourceStore',
			'sys.menu.SYSConfigMenuTreeStore',
			'sys.dictionary.SYSDicTreeStore', 
			'sys.dictionary.SYSDicStore',
			'sys.file.SYSDicFileType', 
			'sys.file.SYSFileStore',
			'sys.file.SYSDicFileHZ',
			'sys.file.SYSMenuIcon'
			]
		});