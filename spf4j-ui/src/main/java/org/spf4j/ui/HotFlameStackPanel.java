/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.annotation.Nullable;
import org.spf4j.base.EqualsPredicate;
import org.spf4j.base.Method;
import org.spf4j.stackmonitor.SampleGraph;
import org.spf4j.stackmonitor.SampleGraph.AggSample;
import org.spf4j.stackmonitor.SampleGraph.SampleKey;
import org.spf4j.stackmonitor.SampleNode;
import static org.spf4j.ui.StackPanelBase.LINK_COLOR;

/**
 * Stack panel implementation that visualizes the profile data via a "flame real graph".
 *
 * @author zoly
 */
@SuppressFBWarnings("SE_BAD_FIELD")
public final class HotFlameStackPanel extends StackPanelBase<SampleKey> {

  private static final long serialVersionUID = 1L;

  private SampleGraph completeGraph;
  private Map<SampleKey, Rectangle2D> methodLocations;
  private double totalHeight = 0;
  private SampleKey startFrom = null;


  public HotFlameStackPanel(final SampleNode samples) {
    super(samples);
  }

  @Override
  public int paint(final Graphics2D gr, final double width, final double rowHeight) {
    paintGraph(gr, (float) width, (float) rowHeight);
    return (int) totalHeight;
  }

  @SuppressFBWarnings("SE_COMPARATOR_SHOULD_BE_SERIALIZABLE")
  private static final class SComparator implements Comparator<AggSample> {

    private final SampleGraph graph;

    SComparator(final SampleGraph graph) {
      this.graph = graph;
    }

    public int compare(final AggSample a, final AggSample b) {
      if (a.getLevel() < b.getLevel()) {
        return -1;
      } else if (a.getLevel() > b.getLevel()) {
        return 1;
      }
      int cd = graph.getChildren(a).size() - graph.getChildren(b).size();
      if (cd < 0) {
        return -1;
      } else if (cd > 0) {
        return 1;
      }
      if (graph.haveCommonChild(a, b)) {
        return 0;
      } else {
        return a.getKey().getMethod().compareTo(b.getKey().getMethod());
      }

    }
  }

  /**
   * method that draws an aggregate sample.
   * @param g2
   * @param sample
   * @param graph
   */
  private void drawMethod(final Graphics2D g2, final AggSample sample, final SampleGraph graph,
          final float pps, final float rowHeight) {
    Set<AggSample> parents = graph.getParents(sample);
    Rectangle2D.Float location;
    final List<Point2D> fromLinks;
    if (sample.getKey().equals(startFrom) || parents.isEmpty()) { // this is the root node.
      location = new Rectangle2D.Float(0, 0, pps * sample.getNrSamples(), rowHeight);
      fromLinks = Collections.EMPTY_LIST;
    } else if (parents.size() == 1) { // single parent
      location = getLocationSingleParent(parents, graph, rowHeight, pps, sample);
      fromLinks = Collections.EMPTY_LIST;
    } else  { // multiple parents
      fromLinks = new ArrayList<>(parents.size());
      Iterator<AggSample> iterator = parents.iterator();
      AggSample next = iterator.next();
      Rectangle2D pr = methodLocations.get(next.getKey());
      double x1 = pr.getX();
      double x2 = pr.getMaxX();
      double y = pr.getMaxY();
      fromLinks.add(new Point2D.Float((float) pr.getCenterX(), (float) y));
      while (iterator.hasNext()) {
        next = iterator.next();
        pr = methodLocations.get(next.getKey());
        fromLinks.add(new Point2D.Float((float) pr.getCenterX(), (float) pr.getMaxY()));
        x1 = Math.min(x1, pr.getX());
        x2 = Math.max(x2, pr.getMaxX());
        y = Math.max(y, pr.getMaxY());
      }
      y += rowHeight;
      float width = pps * sample.getNrSamples();
      Double result = findEmptySpace(x1, y, width, x2);
      while (result == null) {
        // TODO: optimize this to increment with a better value
        y += rowHeight;
        result = findEmptySpace(x1, y, width, x2);
      }
      location = new Rectangle2D.Float(result.floatValue(), (float) y, width, rowHeight);
    }
    methodLocations.put(sample.getKey(), location);
    insert(location, sample.getKey());
    drawMethodRectangle(g2, location, sample, fromLinks);

  }

  public Rectangle2D.Float getLocationSingleParent(final Set<AggSample> parents,
          final SampleGraph graph, final float rowHeight, final float pps, final AggSample sample) {
    // single parent, will be drawed attached.
    AggSample parent = parents.iterator().next();
    Rectangle2D pRect = methodLocations.get(parent.getKey());
    double px = pRect.getX();
    Set<AggSample> sibblings = graph.getChildren(parent);
    for (AggSample sibbling : sibblings) {
      if (graph.getParents(sibbling).size() == 1) {
        Rectangle2D l = methodLocations.get(sibbling.getKey());
        if (l != null) {
          double x = l.getX() + l.getWidth();
          if (x > px) {
            px = x;
          }
        }
      }
    }
    return new Rectangle2D.Float((float) px, (float) pRect.getY() + rowHeight,
            pps * sample.getNrSamples(), rowHeight);
  }


  private void drawMethodRectangle(final Graphics2D g2, final Rectangle2D.Float location,
          final AggSample sample, final List<Point2D> fromLinks) {
    double x = location.getX();
    double y = location.getY();
    double width = location.getWidth();
    double height = location.getHeight();
    double newHeight = y + height;
    if (totalHeight < newHeight) {
      totalHeight = newHeight;
    }
    FlameStackPanel.setElementColor(sample.getLevel(), g2);
    g2.setClip((int) x, (int) y, (int) width, (int) height);
    g2.fillRect((int) x, (int) y, (int) width, (int) height);
    String val = sample.getKey().getMethod().toString() + '-' + sample.getNrSamples();
    g2.setPaint(Color.BLACK);
    g2.drawString(val, (int) x, (int) (y + height - 1));
    g2.setClip(null);
    g2.setPaint(LINK_COLOR);
    int tx = (int) (x + width / 2);
    for (Point2D divLoc : fromLinks) {
      Arrow2D.draw(g2, (int) divLoc.getX(), (int) divLoc.getY(),
              tx, (int) y);
    }
    g2.drawRect((int) x, (int) y, (int) width, (int) height);
  }


  private void paintGraph(
          final Graphics2D g2, final float areaWidth, final float rowHeight) {

    final SampleGraph graph = new SampleGraph(Method.ROOT, getSamples());
    this.completeGraph = graph;
    AggSample aggRoot =  startFrom == null ? graph.getAggRootVertex() : graph.getAggNode(startFrom);
    int rootSamples = aggRoot.getNrSamples();
    final float pps = areaWidth / rootSamples; // calculate pixe/sample
    methodLocations = new HashMap<>();
    PriorityQueue<AggSample> traversal = new PriorityQueue<>(new SComparator(graph));
    traversal.add(aggRoot);
    Set<AggSample> drawed = new HashSet<>(graph.getAggNodesNr());
    AggSample next;
    while ((next = traversal.poll()) != null) {
      if (drawed.add(next)) {
        drawMethod(g2, next, graph, pps, rowHeight);
        traversal.addAll(graph.getChildren(next));
      }
    }
  }

  @Nullable
  private Double findEmptySpace(final double newXBase, final double newYBase,
          final double newWidth, final double maxX) {
    double tryx = newXBase + (maxX - newXBase) / 2 - newWidth / 2;
    List<SampleKey> methods = search(tryx, newYBase, newWidth, Float.MAX_VALUE - newYBase);
    if (!methods.isEmpty()) {
      tryx = newXBase;
      methods = search(tryx, newYBase, newWidth, Float.MAX_VALUE  - newYBase);
      if (!methods.isEmpty()) {
        tryx = maxX - newWidth;
        methods = search(tryx, newYBase, newWidth, Float.MAX_VALUE  - newYBase);
      }
    }
    if (methods.isEmpty()) {
      return tryx;
    } else {
      return null;
    }
  }

  @Override
  @Nullable
  public String getDetail(final Point location) {
    List<SampleKey> tips = search(location.x, location.y, 0, 0);
    if (tips.size() >= 1) {
      final SampleKey key = tips.get(0);
      final AggSample node = completeGraph.getAggNode(key);
      StringBuilder sb = new StringBuilder();
      sb.append(node).append('-').append(node.getNrSamples())
              .append("\n invoked from: ");
      appendEdgeInfo(completeGraph.getParents(node), sb);
      sb.append("\n invoking: ");
      appendEdgeInfo(completeGraph.getChildren(node), sb);
      return sb.toString();
    } else {
      return null;
    }
  }

  @Override
  public void filter() {
    List<SampleKey> tips = search(xx, yy, 0, 0);
    if (tips.size() >= 1) {
      final SampleKey value = tips.get(0);
      updateSamples(getMethod(), getSamples().filteredBy(new EqualsPredicate<>(value.getMethod())));
      repaint();
    }
  }

  @Override
  public void drill() {
    List<SampleKey> tips = search(xx, yy, 0, 0);
    if (tips.size() >= 1) {
      startFrom = tips.get(0);
      repaint();
    }
  }

  private static void appendEdgeInfo(final Set<AggSample> incomming,
          final StringBuilder sb) {
    for (AggSample entry : incomming) {
      sb.append(entry.getKey()).append('-').append(entry.getNrSamples()).append("; ");
    }
  }

}
