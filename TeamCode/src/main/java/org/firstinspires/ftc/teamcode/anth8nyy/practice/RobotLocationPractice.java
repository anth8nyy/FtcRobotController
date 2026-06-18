package org.firstinspires.ftc.teamcode.anth8nyy.practice;

public class RobotLocationPractice {
    double angle;
    double x;
    double y;

    //constructor method
    public RobotLocationPractice(double angle) {
        this.angle = angle;
    }

    public double getHeading() {
        /* this method normalizes robot heading between -180 and 180 */
        double angle = this.angle;
        while (angle > 180) {
            angle -= 360;
        }
        while (angle <= -180) {
            angle += 360;
        }
        return angle;
    }

    public void turnRobot(double angleChange) {
        angle += angleChange;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public double getAngle(){
        return this.angle;}
    public void changeX(double changeAmount){
        x += changeAmount;
    }
    public void setX(double x){
        this.x = x;
    }
    public double getX(){
        return this.x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void changeY(double changeAmount){
        y += changeAmount;
    }
    public double getY(){
        return getY();
    }


}
