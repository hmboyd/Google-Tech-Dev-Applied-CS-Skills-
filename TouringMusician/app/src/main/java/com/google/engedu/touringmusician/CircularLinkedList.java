/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        public Node(Point point) {
            this.point = point;
        }
    }

    Node head;


    public void insertBeginning(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node newNode = new Node(p);
        if (head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;

        } else {

            Node previousNode = head.prev;
            newNode.next = head;
            previousNode.next = newNode;
            head.prev = newNode;
            newNode.prev = previousNode;

        }
        head = newNode;
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node tempNode = head;
        if (tempNode == null) {
            return 0;
        }

        if (tempNode.next == head) {
            return 0;
        }

        Node tempNode2 = head.next;

        while (tempNode2 != head) {
            total += distanceBetween(tempNode.point, tempNode2.point);
            tempNode = tempNode.next;
            tempNode2 = tempNode2.next;
        }

        return total;
    }

    public void insertNearest(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        if (head == null) {
            head = new Node(p);
        } else {
            float minDistance = Float.MAX_VALUE;

            Node tempNode = head;
            Node minDistanceNode = null;

            for (Point point : this) {
                float distance = distanceBetween(point, p);
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceNode = tempNode;
                }
                tempNode = tempNode.next;
            }

            if (minDistanceNode.next != head) {
                Node nextNode = minDistanceNode.next;
                Node newNode = new Node(p);

                minDistanceNode.next = newNode;
                newNode.prev = minDistanceNode;
                newNode.next = nextNode;
            } else {
                Node newNode = new Node(p);

                Node nextNode = minDistanceNode.next;
                minDistanceNode.next = newNode;
                newNode.next = nextNode;
                newNode.prev = minDistanceNode;
                nextNode.prev = newNode;
            }

        }
    }

    public void insertSmallest(Point p) {

        if (head == null) {
            head = new Node(p);
        } else if (head.next == null) {
            Node newNode = new Node(p);
            head.next = newNode;
            newNode.prev = head;
        } else {
            float overAllDistance = totalDistance();
            float smallest = Float.MAX_VALUE;
            float total;

            Node tempNode = head, closest = null;

            for (Point point : this) {
                float distance = distanceBetween(p, point);
                total = overAllDistance + distance;

                if (total < smallest) {
                    smallest = total;
                    closest = tempNode;
                }
                tempNode = tempNode.next;
            }


            if (closest.next != head) {
                Node nextNode = closest.next;
                Node newNode = new Node(p);

                closest.next = newNode;
                newNode.prev = closest;
                newNode.next = nextNode;
            } else {
                Node newNode = new Node(p);

                Node nextNode = closest.next;
                closest.next = newNode;
                newNode.next = nextNode;
                newNode.prev = closest;
                nextNode.prev = newNode;
            }
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
