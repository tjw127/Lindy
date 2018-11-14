package co.uglytruth.lindy.abstract_classes;

public abstract class WalmartRestApiTemplate {

    public Object getResults(){
        Object step_one = stepOne();
        Object step_two = stepTwo(step_one);
        return  stepThree(step_two);

    }

    protected abstract Object stepOne();
    protected abstract Object stepTwo(Object step_one);
    protected abstract Object stepThree(Object step_two);

}
