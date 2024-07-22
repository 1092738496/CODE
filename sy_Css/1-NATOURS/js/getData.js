// 使用Fetch API从接口获取数据
fetch('http://125.122.20.253:9527/rateServer/xin/2584959')
    .then(response => {
        // 检查响应是否成功
        if (!response.ok) {
            throw new Error('网络响应错误');
        }
        // 解析JSON格式的响应数据
        return response.json();
    })
    .then(data => {
        // 在这里处理data，data是解析后的JSON对象
        console.log(data);
    })
    .catch(error => {
        // 在这里处理错误
        console.error('获取数据时发生错误:', error);
    });