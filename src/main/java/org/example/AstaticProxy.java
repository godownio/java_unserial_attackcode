package org.example;

public class AstaticProxy implements Ainterface{
    private Ainterface aimpl;
    public AstaticProxy(Ainterface a){
        this.aimpl = a;
    }
    public void display()
    {
        aimpl.display();
        System.out.println("调用了display");
    }
    public void select(){
        aimpl.select();
        System.out.println("调用了select");
    }
    public void add()
    {
        aimpl.add();
        System.out.println("调用了add");
    }
}
