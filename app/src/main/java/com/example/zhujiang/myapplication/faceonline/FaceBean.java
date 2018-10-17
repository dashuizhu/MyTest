package com.example.zhujiang.myapplication.faceonline;

import android.util.Log;
import lombok.Data;

/**
 * @author zhuj 2018/4/4 下午4:22.
 */
public class FaceBean {

  /**
   * position : {"top":89,"left":129,"right":443,"bottom":403}
   * landmark : {"left_eyebrow_left_corner":{"x":406,"y":147},"left_eyebrow_middle":{"x":408,"y":186},"left_eyebrow_right_corner":{"x":403,"y":226},"right_eyebrow_left_corner":{"x":396,"y":288},"right_eyebrow_middle":{"x":403,"y":330},"right_eyebrow_right_corner":{"x":395,"y":371},"left_eye_left_corner":{"x":370,"y":165},"left_eye_right_corner":{"x":369,"y":216},"right_eye_left_corner":{"x":363,"y":297},"right_eye_right_corner":{"x":361,"y":350},"nose_left":{"x":283,"y":207},"nose_bottom":{"x":265,"y":248},"nose_right":{"x":278,"y":290},"mouth_upper_lip_top":{"x":229,"y":246},"mouth_middle":{"x":210,"y":245},"mouth_lower_lip_bottom":{"x":186,"y":243},"left_eye_center":{"x":370,"y":189},"right_eye_center":{"x":364,"y":321},"nose_top":{"x":293,"y":245},"mouth_left_corner":{"x":210,"y":188},"mouth_right_corner":{"x":207,"y":301}}
   */



  private LandmarkBean landmark;
  /**
   * position : {"top":89,"left":129,"right":443,"bottom":403}
   */

  private PositionBean position;

  public LandmarkBean getLandmark() {
    return landmark;
  }

  public void setLandmark(LandmarkBean landmark) {
    this.landmark = landmark;
  }

  public PositionBean getPosition() {
    return position;
  }

  public void setPosition(PositionBean position) {
    this.position = position;
  }

  @Data
  public static class LandmarkBean {
    /**
     * left_eyebrow_left_corner : {"x":406,"y":147}
     * left_eyebrow_middle : {"x":408,"y":186}
     * left_eyebrow_right_corner : {"x":403,"y":226}
     * right_eyebrow_left_corner : {"x":396,"y":288}
     * right_eyebrow_middle : {"x":403,"y":330}
     * right_eyebrow_right_corner : {"x":395,"y":371}
     * left_eye_left_corner : {"x":370,"y":165}
     * left_eye_right_corner : {"x":369,"y":216}
     * right_eye_left_corner : {"x":363,"y":297}
     * right_eye_right_corner : {"x":361,"y":350}
     * nose_left : {"x":283,"y":207}
     * nose_bottom : {"x":265,"y":248}
     * nose_right : {"x":278,"y":290}
     * mouth_upper_lip_top : {"x":229,"y":246}
     * mouth_middle : {"x":210,"y":245}
     * mouth_lower_lip_bottom : {"x":186,"y":243}
     * left_eye_center : {"x":370,"y":189}
     * right_eye_center : {"x":364,"y":321}
     * nose_top : {"x":293,"y":245}
     * mouth_left_corner : {"x":210,"y":188}
     * mouth_right_corner : {"x":207,"y":301}
     */

    private FacePoint left_eyebrow_left_corner;
    private FacePoint left_eyebrow_middle;
    private FacePoint left_eyebrow_right_corner;
    private FacePoint right_eyebrow_left_corner;
    private FacePoint right_eyebrow_middle;
    private FacePoint right_eyebrow_right_corner;
    private FacePoint left_eye_left_corner;
    private FacePoint left_eye_right_corner;
    private FacePoint right_eye_left_corner;
    private FacePoint right_eye_right_corner;
    private FacePoint nose_left;
    private FacePoint nose_bottom;
    private FacePoint nose_right;
    private FacePoint mouth_upper_lip_top;
    private FacePoint mouth_middle;
    private FacePoint mouth_lower_lip_bottom;
    private FacePoint left_eye_center;
    private FacePoint right_eye_center;
    private FacePoint nose_top;
    private FacePoint mouth_left_corner;
    private FacePoint mouth_right_corner;
  }

  public static class FacePoint {
    int x;
    int y;
  }

  /**
   * 是否摇头
   * @return
   */
  public boolean isShake() {
    if (landmark == null) {
      return false;
    }
    FacePoint left_eye_center = landmark.getLeft_eye_center();
    FacePoint right_eye_center = landmark.getRight_eye_center();
    FacePoint mouth_middle = landmark.getMouth_middle();

    int eye_middle = (left_eye_center.y + right_eye_center.y) / 2;
    int value = (eye_middle - mouth_middle.y);
    boolean isShake= Math.abs(value) > 30;
    Log.w("test", "摇头 " + isShake+ "  " + value);
    return isShake;
  }

  //张嘴
  public boolean isOpenMouth() {
    if (landmark == null) {
      return false;
    }
    FacePoint mouth_upper_lip_top = landmark.getMouth_upper_lip_top();
    FacePoint mouth_lower_lip_bottom = landmark.getMouth_lower_lip_bottom();

    FacePoint nose_bottom = landmark.getNose_bottom();
    FacePoint nose_top = landmark.getNose_top();
    int dissNose = Math.abs(nose_top.x - nose_bottom.x);

    int dissMouth = Math.abs((mouth_upper_lip_top.x - mouth_lower_lip_bottom.x));
    //发现右边偏头识别率很低
    int length = getPosition().getBottom() - getPosition().getTop();

    boolean isopen= dissMouth > 40;

    Log.w("test", "张嘴 " + isopen + "  " + dissMouth + " nose:" + length );

    return isopen;
  }

  public static class PositionBean {
    /**
     * top : 89
     * left : 129
     * right : 443
     * bottom : 403
     */

    private int top;
    private int left;
    private int right;
    private int bottom;

    public int getTop() {
      return top;
    }

    public void setTop(int top) {
      this.top = top;
    }

    public int getLeft() {
      return left;
    }

    public void setLeft(int left) {
      this.left = left;
    }

    public int getRight() {
      return right;
    }

    public void setRight(int right) {
      this.right = right;
    }

    public int getBottom() {
      return bottom;
    }

    public void setBottom(int bottom) {
      this.bottom = bottom;
    }
  }
}
