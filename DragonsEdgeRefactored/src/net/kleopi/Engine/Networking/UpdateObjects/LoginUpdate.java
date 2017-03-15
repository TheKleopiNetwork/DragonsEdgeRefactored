package net.kleopi.Engine.Networking.UpdateObjects;

public class LoginUpdate extends UpdateObject
{
	private static final long	serialVersionUID	= -2686395513341337013L;
	public String				password;
	public String				username;

	public LoginUpdate(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	public LoginUpdate(){}
}
