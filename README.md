# ShapeDrawing
A simple drawing program using shapes from awt package

Watch [youtube demo](https://youtu.be/oTU7bWZ3zPg)

##Run & Compile
* **Compile** - javac -cp ".;ImageTracer.jar;" CartoonDrawing.java
* **Run** - java -cp ".;ImageTracer.jar;" CartoonDrawing
* **Compile jar (sample)** - jar cvfm CartoonDrawing.jar Manifest.txt -C:/Users/username/Desktop/CartoonDrawing/ .

## Important
* Use data.txt to change the save/load path and image path

## Samples (original(far left) -  then drawings)
![original spiderman](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/spidey.png)
![sample v1 spiderman drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/v1.PNG)
![sample v2 spiderman drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/v2.PNG)
![original bike](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/bike.jpg)
![sample bike drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/bike_drawing_sample.PNG)
![ass in mousepad](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/VSpecial.jpg)
![ass in mousepad drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/VSpecial_drawing.PNG)

## Samples SVG types using ImageTracer
![sample v1 spiderman svg drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/v1_svg.png)
![sample v2 spiderman svg drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/v2_svg.png)
![sample bike svg drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/bike_drawing_sample_svg.png)
![ass in mousepad svg drawing](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/VSpecial_drawing_svg.png)

## Important Controls
* **[F]** - toggle draw or fill shape
* **[H]** - toggle hide or show image
* **[J]** - toggle hide or show drawing
* **[Z]** - undo(becareful no redo yet)
* **[Q]** - change shape to draw
* **[Mouse Wheel]** - stroke of the line of shape
* **[Left click mouse]** - draw
* **[middle click mouse]** - ctrlPoint1 - for curves
* **[right click mouse]** - ctrlPoint2 - for curves
* **[SPACE]** - to add shape to memory
* **[S]** - save current work(memory) to filepath - replace previous
* **[L]** - load from file path previous work
* **[P]** - save to png image format
* **[O]** - save to svg image format(may produce low quality results)

## References
* [ImageTracer by jankovicsandras](https://github.com/doppelgunner/ShapeDrawing/blob/master/images/VSpecial_drawing.PNG)


