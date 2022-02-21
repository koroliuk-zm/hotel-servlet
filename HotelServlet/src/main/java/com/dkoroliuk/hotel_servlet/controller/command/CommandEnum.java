package com.dkoroliuk.hotel_servlet.controller.command;

/**
 * Enumeration for all commands
 */
public enum CommandEnum {
	LOGIN {
		{
			command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			command = new LogoutCommand();
		}
	},

	REGISTER {
		{
			command = new RegisterCommand();
		}
	},

	DEFAULT {
		{
			command = new DefaultCommand();
		}
	},

	CHANGELOCALE {
		{
			command = new ChangeLocaleCommand();
		}
	},
	REGISTERUSER {
		{
			command = new RegisterUserCommand();
		}
	},
	GETALLROOMS {
		{
			command = new GetAllRoomsCommand();
		}
	},
	ADMINPAGE {
		{
			command = new AdminPageCommand();
		}
	},
	GETALLUSERS {
		{
			command = new GetAllUsersCommand();
		}
	},
	UPDATEUSERPAGE {
		{
			command = new UpdateUserPageCommand();
		}
	},
	UPDATEUSER {
		{
			command = new UpdateUserCommand();
		}
	},
	DELETEUSER {
		{
			command = new DeleteUserCommand();
		}
	},
	ADDROOMLINK {
		{
			command = new AddRoomLinkCommand();
		}
	},
	ADDROOM {
		{
			command = new AddRoomCommand();
		}
	},
	UPDATEROOMPAGE {
		{
			command = new UpdateRoomPageCommand();
		}
	},
	UPDATEROOM {
		{
			command = new UpdateRoomCommand();
		}
	},
	DELETEROOM {
		{
			command = new DeleteRoomCommand();
		}
	},
	ADDUSERLINK {
		{
			command = new AddUserLinkCommand();
		}
	},
	ADDUSER {
		{
			command = new AddUserCommand();
		}
	},
	ORDERPAGE {
		{
			command = new OrderPageCommand();
		}
	},
	ORDERROOM {
		{
			command = new OrderRoomCommand();
		}
	},
	REQUESTPAGE {
		{
			command = new RequestPageCommand();
		}
	},
	MAKEREQUEST {
		{
			command = new MakeRequestCommand();
		}
	},
	GETPRIVATECABINET {
		{
			command = new GetPrivateCabinetCommand();
		}
	},
	PAYROOMORDER {
		{
			command = new PayRoomOrderCommand();
		}
	},
	WAITERPAGE {
		{
			command = new WaiterPageCommand();
		}
	},
	PROCESSREQUESTPAGE {
		{
			command = new ProcessRequestPageCommand();
		}
	},
	CANCELORDER {
		{
			command = new CancelOrderCommand();
		}
	},
	PROCESSREQUEST {
		{
			command = new ProcessRequestCommand();
		}
	};

	Command command;

	Command getCommand() {
		return command;
	}
}