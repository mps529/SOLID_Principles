package com.mslavin.solidprinciples;

/*                                              Liskov Substitution Principle
        Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it.

        At its heart LSP is about interfaces and contracts as well as how to decide when to extend a class vs. use another strategy such as composition to achieve your goal.*/


class RectangleLSP {

    protected int width, height;

    public RectangleLSP() {
    }

    public RectangleLSP(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {return width*height;}

    @Override
    public String toString() {
        return "RectangleLSP{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
    //will work fine if you only need to check for squares, and dont need explicit squares, if you do you need a factory
    public boolean isSquare() {
        return width == height;
    }
}
class SquareLSP extends RectangleLSP {
    public SquareLSP() {
    }

    public SquareLSP(int size) {
        width = height = size;
    }

    /*The following setters break LSP, because since a square has equal sides, setting them independently does not make sense, and can cause errors, see output*/
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}

//using a factory to set the diff implementations of rectangle can help avoid breaking this principle
class RectangleFactory {
    public static RectangleLSP newRectangleLSP (int width, int height) {
        return new RectangleLSP(width, height);
    }
    public static RectangleLSP newSquare (int side) {
        return new RectangleLSP(side, side);
    }
}

class DemoLSP {
    static void useIt(RectangleLSP r) {
        int width = r.getWidth();
        r.setHeight(10);
        //area = width *10
        System.out.println(
                "Expect area of " + (width * 10) + ", got " + r.getArea()
        );
    }

    public static void main(String[] args) {
        RectangleLSP rc = new RectangleLSP(2,3);
        useIt(rc);

        RectangleLSP sq = new SquareLSP();
        sq.setWidth(5);
        useIt(sq);
    }
}
