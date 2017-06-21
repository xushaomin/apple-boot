package com.appleframework.boot;

import com.appleframework.boot.core.CommandOption;
import com.appleframework.boot.utils.ApplicationNameUtils;

public class StartUpInit {

	public static void init(String[] args) {

		// 设置应用名称
		ApplicationNameUtils.setToThreadName();

		// 显示版本号
		Version.logVersion();

		// 处理启动参数
		CommandOption.parser(args);
	}

}
