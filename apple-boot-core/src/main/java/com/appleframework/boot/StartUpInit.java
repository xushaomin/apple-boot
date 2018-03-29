package com.appleframework.boot;

import com.appleframework.boot.core.CommandOption;

public class StartUpInit {

	public static void init(String[] args) {

		// 显示版本号
		Version.logVersion();

		// 处理启动参数
		CommandOption.parser(args);

	}

}
