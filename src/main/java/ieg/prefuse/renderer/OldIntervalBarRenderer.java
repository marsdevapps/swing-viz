package ieg.prefuse.renderer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import prefuse.Constants;
import prefuse.render.AbstractShapeRenderer;
import prefuse.visual.VisualItem;

/**
 * <p>
 * Renders a {@link VisualItem} with an interval as a rectangle (e.g., Gantt
 * chart, LifeLines).
 * </p>
 * The interval is determined by the {@link VisualItem}'s coordinates (by
 * default {@link VisualItem#X} and {@link VisualItem#getX()}) and the
 * {@link OldIntervalBarRenderer}s maxX (see
 * {@link OldIntervalBarRenderer#OldIntervalBarRenderer(String)} and
 * {@link OldIntervalBarRenderer#getMaxXField()}) field. The rendered height is
 * determined by the {@link VisualItem}'s size field and the base size.
 * 
 * <p>
 * Added: 2012-06-13 / AR (based on work by Peter Weishapl)<br>
 * Modifications: 2012-06-13 / AR / no label; height set by size field; y axis
 * </p>
 * 
 * @author peterw
 * @see IntervalLayout
 */
@Deprecated
public class OldIntervalBarRenderer extends AbstractShapeRenderer {
    protected Rectangle2D bounds = new Rectangle2D.Double();

    private int m_axis = Constants.X_AXIS;

    protected String maxXField;

    private int m_baseSize = 10;

    /**
     * Create a {@link OldIntervalBarRenderer}. Uses the given text data field to
     * draw it's text label and the maxX data field to determine the interval to
     * be rendered, which is from {@link VisualItem}s x data field to the given
     * maxX data field.
     * 
     * @param textField
     *            the data field used for the text label
     * @param maxXField
     *            the data field used for the interval
     */
    public OldIntervalBarRenderer(String maxXField) {
        this.maxXField = maxXField;
    }

    public OldIntervalBarRenderer(String maxXField, int m_baseSize) {
        this.maxXField = maxXField;
        this.m_baseSize = m_baseSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see prefuse.render.AbstractShapeRenderer#render(java.awt.Graphics2D,
     * prefuse.visual.VisualItem)
     */
    public void render(Graphics2D g, VisualItem item) {
        renderBackground(g, item);
        renderText(g, item, (int) item.getX());
    }

    /**
     * Renders the background. Override this method to customize background
     * painting.
     * 
     * @param g
     *            graphics object
     * @param item
     *            the item to be rendered
     */
    protected void renderBackground(Graphics2D g, VisualItem item) {
        super.render(g, item);
    }

    /**
     * Renders the text of the given item at the items y and the given x
     * position. The text may exceed the items bounds.
     * 
     * @param g
     *            graphics object
     * @param item
     *            the item to be rendered
     * @param x
     *            the x position of the text
     */
    protected void renderText(Graphics2D g, VisualItem item, int x) {
        // do nothing; override if desired
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * prefuse.render.AbstractShapeRenderer#getRawShape(prefuse.visual.VisualItem
     * )
     */
    protected Shape getRawShape(VisualItem item) {
        double startX = item.getX();
        double endX = item.getDouble(maxXField);
        double startY = item.getY();
        double height = m_baseSize * item.getSize();

        if (Constants.X_AXIS == m_axis) {
            double width = endX - startX;
            bounds.setFrame(startX, startY - height / 2, width, height);
        } else {
            double width = endX - startY;
            System.out.println(startX - height / 2 + " " + startY + " "
                    + height + " " + width);
            bounds.setFrame(startX - height / 2, startY, height, width);
        }

        return bounds;
    }

    public String getMaxXField() {
        return maxXField;
    }

    public void setMaxXField(String maxXField) {
        this.maxXField = maxXField;
    }

    /**
     * Sets the base size, in pixels, for shapes drawn by this renderer. The
     * base size is the width and height value used when a VisualItem's size
     * value is 1. The base size is scaled by the item's size value to arrive at
     * the final scale used for rendering.
     * 
     * @param size
     *            the base size in pixels
     */
    public void setBaseSize(int size) {
        m_baseSize = size;
    }

    /**
     * Returns the base size, in pixels, for shapes drawn by this renderer.
     * 
     * @return the base size in pixels
     */
    public int getBaseSize() {
        return m_baseSize;
    }

    /**
     * Return the axis type of this layout, either
     * {@link prefuse.Constants#X_AXIS} or {@link prefuse.Constants#Y_AXIS}.
     * 
     * @return the axis type of this layout.
     */
    public int getAxis() {
        return m_axis;
    }

    /**
     * Set the axis type of this layout.
     * 
     * @param axis
     *            the axis type to use for this layout, either
     *            {@link prefuse.Constants#X_AXIS} or
     *            {@link prefuse.Constants#Y_AXIS}.
     */
    public void setAxis(int axis) {
        if (axis < 0 || axis >= Constants.AXIS_COUNT)
            throw new IllegalArgumentException("Unrecognized axis value: "
                    + axis);
        m_axis = axis;
    }
}
