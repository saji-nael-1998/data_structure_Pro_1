package application;

public class LinkedList {
//define the head of the linked list 
	Node head;

	public void insertFirst(Object data) {
		if (head == null) {
			head = new Node(data);
			return;
		} else {
			Node temp = head;
			head = new Node(data);
			head.next = temp;
			return;
		}
	}

	public void insert(Object data) {
		if (head == null) {
			insertFirst(data);
			return;
		} else {
			Node n = head;
			while (n.next != null) {

				n = n.next;
			}
			n.next = new Node(data);
			return;
		}
	}

	public void insertAfter(int key, Object data) {
		try {
			if (key == 0) {
				insertFirst(data);
				return;
			} else if (key > 0) {
				Node n = head;
				int counter = 0;
				while (counter != key - 1) {

					n = n.next;
					counter++;
				}
				Node temp = n.next;
				n.next = new Node(data);
				n.next.next = temp;
				return;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Out Of Index");
		}
	}

	public void delete(int key) {
		try {
			if (head == null) {
				System.out.println("The array is empty");
				return;
			} else {
				if (key == 0) {
					head = head.next;
				} else if (key > 0) {
					Node n = head;
					int counter = 0;
					while (counter != key - 1) {
						n = n.next;
						counter++;
					}

					n.next = n.next.next;

					return;

				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			System.out.println("Out Of Index");
		}
	}

	public void swap(int key1, int key2) {
		try {
			if (key1 < 0 || key2 < 0) {
				throw new Exception();
			} else {
				Node n1 = head;
				for (int x = 0; x < key1; x++) {
					n1 = n1.next;
				}

				Node n2 = head;
				for (int x = 0; x < key2; x++) {
					n2 = n2.next;
				}
				Object temp = n1.data;
				n1.data = n2.data;
				n2.data = temp;

			}
		} catch (Exception e) {
			System.out.println("Out Of Index");
		}
	}

	public Object get(int key) {
		Node n = head;
		for (int x = 0; x < key; x++) {
			n = n.next;
		}
		return n.data;
	}

	public int size() {
		if (head == null) {

			return 0;
		} else {
			Node n = head;
			int counter = 0;
			while (n != null) {
				counter++;
				n = n.next;
			}
			return counter;
		}
	}
}
