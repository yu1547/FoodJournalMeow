(1)
先下載FoodJournalMeow/out/artifact/第12組_貓掌食記_Java2024_jar zip檔，解壓縮，再執行裡面的第12組_貓掌食記_Java2024.jar檔
(2)
1. 生成食記圖的png及pdf檔增加了預設檔名
2. 修改了211餐盤的建議欄位
3. 修改身高體重空白欄位處理
4. UML圖修改箭頭
(3)
211餐盤技術說明：
用MouseAdapter實現滑鼠事件
mousePressed: 如果有圖像，會清除當前的繪圖路徑並記錄滑鼠按下的起始點
mouseDragged: 如果有圖像，會記錄滑鼠拖動過的每個點，形成一條路徑。
mouseReleased: 如果有圖像，會記錄滑鼠釋放的結束點，將當前路徑加到所有路徑中，記錄當前使用的顏色，然後清除當前路徑以便下次繪圖使用。
在createPathFromPoints()，用Path2D建立一個path，把每一個點連接形成一條路徑，且為了計算面積設定為封閉的
在paintComponent中，創建一個新的 Graphics2D 對象來繪製所有已記錄的路徑，每條路徑使用對應的顏色
在calculateArea，使用多邊形面積公式來計算每條路徑的面積
在calculateAreasByColor，按照顏色計算面積
