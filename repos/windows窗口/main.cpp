#include <Windows.h>

LRESULT CALLBACK Wndproc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam) {
	switch (uMsg) {
	case WM_CREATE:
		MessageBoxW(hWnd, L"窗口创建了",L"提示", MB_OK);
		break;
	case WM_CLOSE:
		MessageBoxW(hWnd, L"窗口关闭了", L"提示", MB_OK);
		DestroyWindow(hWnd);
		PostQuitMessage(0);
		break;
	}
	return DefWindowProcW(hWnd, uMsg, wParam, lParam);
}

int WINAPI WinMain(
	HINSTANCE hInstance,
	HINSTANCE hPrevInstance,
	LPSTR     lpCmdLine,
	int       nShowCmd
) {
	//1.创建一个窗口类
	WNDCLASSW wndclassw = { 0 };
	wndclassw.lpszClassName = L"1 to chuangkou";
	wndclassw.lpfnWndProc = Wndproc;
	//2.注册窗口类
	RegisterClassW(&wndclassw);
	//3.创建窗口
	HWND hwindow = CreateWindowW(
		wndclassw.lpszClassName,
		L"1 to chuangkou",
		WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT,
		0,
		CW_USEDEFAULT,
		0,
		nullptr,
		nullptr,
		hInstance,
		0
	);
	//4.显示窗口
	ShowWindow(hwindow, SW_SHOWNORMAL);

	//5.获取消息
	MSG msg = { 0 };
	while (GetMessageW(&msg, 0, 0, 0)) {
		DispatchMessageW(&msg);
	};

	return 0;
}