package com.abb.ventyx.utilities;

public class LoadingWaiting {
	public static void waitToLoadPage(int timeAsMilisecond) {
		try {
			Thread.sleep(timeAsMilisecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
