// domesA2BSTDict.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <list>
#include <sstream>

#include <Windows.h>

using namespace std;
//Το δυαδικο δεντρο αναζήτησης θα περιέχει κομβους.
//Καθε κομβος θα περιέχει ένα integer ο οποίος αναπαριστά το επίπεδο, 2 string την λεξη στα αγγλικα και την μετάφραση αντίστοιχα
//τέλος pointers στο αριστερό και δεξιό παιδί (κόμβο) του υποδέντρου.
class Node{
	public:
		int nodeLevel = 1;
		
		string word = "";
		string translation = "";
		Node *leftChild = NULL;
		Node *rightChild = NULL;

	/*Node::~Node(){
		delete this;
	}*/
		
};

//BST creation
//To BST αποτελείται από κόμβους της κλάσης Node.
class BinarySearchTree {
	public:
		//αποθηκευεται το μεγιστο υψος καθε φορα που γινεται insert
		int maxLevel = 1;
		std::list<string> toPrint;
		int totalNodes = 0;
		Node *root = NULL;
		
		int printOrder = 0;


		void printLevelOrder(Node* root)
		{
			int h = height(root);
			int i;
			for (i = 1; i <= h; i++) {
				printGivenLevel(root, i);
				cout << endl;
			}
		}

		int height(Node* node)
		{
			if (node == NULL)
				return 0;
			else
			{
				/* compute the height of each subtree */
				int lheight = height(node->leftChild);
				int rheight = height(node->rightChild);

				/* use the larger one */
				if (lheight > rheight)
					return(lheight + 1);
				else return(rheight + 1);
			}
		}


		void printGivenLevel(Node* root, int level)
		{
			if (root == NULL) {

				return;
			}
			if (level == 1) {
				
				cout << root->word;
			//	if (level > 2) p - 1;
				
			}
			else if (level > 1)
			{
				
				printGivenLevel(root->leftChild, level - 1);
				printGivenLevel(root->rightChild, level - 1);
			}
		}



		Node *search(string word) {
			list<Node *>words;
			this->Inorder(this->root, words, word);
			cout << "The words that match are:\n";
			for (auto v : words){
				std::cout << v->word << "\n";
		}	
			string wordToReturn;
			cout << "Choose for which word you want to see the translation"<<endl;
			cin >> wordToReturn;
			Node *n = NULL;
			n = this->root;
			while (n != NULL)
			{
				if (n->word == wordToReturn) {
					cout << n->translation;
					return n;
				}
				if (n != NULL) {
					if (wordToReturn < n->word) {
						n = n->leftChild;
					}
				}
				if (n != NULL) {
					if (wordToReturn > n->word) {
						n = n->rightChild;
					}
				}
			}
			cout <<"Word not found" ;
			return n;
		}
		bool insert(string word, string translation) {

			//if the rood in NULL the we add the word and its translation 
			//to the root Node.
			if (this->root == NULL) {
				root = new Node();
				root->word = word;
				root->translation = translation;
				this->root = root;
				this->totalNodes++;
				return true;
			}
			int countlevels = 1;
			//δεικτης που δειχνει στη ριζα
			Node *n = NULL;
			n = root;
			while (n != NULL) {
				//Αν η λεξη ειναι μικροτερη αλφαβητικά της λέξης του κομβού συνεχίζω προς το αριστερό παιδι αν αυτο υπαρχει.
				if (word < n->word && n->word != "" && n->leftChild != NULL) {
					n = n->leftChild;
					countlevels++;
				}
				//Αν η λεξη ειναι μικροτερη αλφαβητικά της λέξης του κομβού συνεχίζω και δεν υπαρχει αριστερο παιδι
				//το δημιουργω και αποθηκευω την λεξη
				if (word < n->word && n->word != "" && n->leftChild == NULL) {
					countlevels++;
					Node *leftChild = NULL;
					leftChild = new Node();
					leftChild->word = word;
					leftChild->translation = translation;
					leftChild->nodeLevel = countlevels;
					if (countlevels > this->maxLevel) {
						maxLevel = countlevels;
					}
					this->totalNodes++;
					n->leftChild = leftChild;
					return true;
				}

				//Αν η λεξη ειναι μεγαλύτερη αλφαβητικά της λέξης του κομβού συνεχίζω προς το δεξί παιδι αν αυτο υπαρχει.
				if (word > n->word && n->word != "" && n->rightChild != NULL) {
					n = n->rightChild;
					countlevels++;
				
				}
				if (word > n->word && n->word != "" && n->rightChild == NULL) {
					countlevels++;
					Node *rightChild = NULL;
					rightChild = new Node();
					rightChild->word = word;
					rightChild->translation = translation;
					rightChild->nodeLevel = countlevels;
					if (countlevels > this->maxLevel) {
						maxLevel = countlevels;
					}
					this->totalNodes++;
					n->rightChild = rightChild;
					return true;

				}

				//Αν ο κόμβος μας έχει τιμή Null τότε αυτό σημαίνει ότι δεν υπάρχει κάποια λέξη αποθηκευμένει σ αυτόν
				//Οποτε η λεξη αποθηκεύεται.
				/*if (root = NULL) {
					root = new Node();
					root->word = word;
					root->translation = translation;
					root->nodeLevel = countlevels++;
					return true;
				}*/
			}
			return false;
		}

		//βρισκει το μικροτερο σε τιμη φυλλο μεσα σε ενα δεντρο.
			 Node *getSmallestLeaf(Node *subtreeRoot) {
				 Node *n = NULL;
				 Node *prevNode = NULL;
			while (subtreeRoot != NULL) {
				if (subtreeRoot->rightChild != NULL && subtreeRoot->rightChild->leftChild != NULL) {

				}
				if (subtreeRoot->leftChild == NULL && subtreeRoot->rightChild == NULL) {
					n = new Node();
					n->word = subtreeRoot->word;
					n->translation = subtreeRoot->translation;
					n->leftChild = NULL;
					n->rightChild = NULL;
					//delete subtreeRoot;
					subtreeRoot = NULL;
					//θετουμε το παιδι του γονέα null αφου θα το μετακινησουμε
					prevNode->leftChild = NULL;
					break;
				}
				//κραταμε τον γονεα του φυλλου
				if (subtreeRoot->leftChild != NULL && subtreeRoot->leftChild->leftChild == NULL) {
					prevNode = subtreeRoot;
				}
				n = subtreeRoot;
				subtreeRoot = subtreeRoot->leftChild;
				
			}
			
			
			//Αν η επαναληψη δεν μπει στο if τοτε σημαινει οτι δεν υπαρχει καποιο αριστερο φυλο στους
			//κομβους που εχουν κοντινες τιμες με τον κομβο που διαγραφεται
			//οποτε ο πιο κοντινος σε τιμη κομβος θα ειναι το δεξι παιδι και αφου δεν υπαρχει αριστερο η επαναληψη σταματα
			return n;
		}
			 Node * minValueNode(Node* node)
			 {
				 Node* current = node;

				 /* loop down to find the leftmost leaf */
				 while (current && current->leftChild != NULL)
					 current = current->leftChild;

				 return current;
			 }

			  Node* deleteNode(Node* root, string word)
			 {
				 // base case 
				 if (root == NULL) return root;

				 // If the key to be deleted is smaller than the root's key, 
				 // then it lies in left subtree 
				 if (word < root->word)
					 root->leftChild = deleteNode(root->leftChild, word);

				 // If the key to be deleted is greater than the root's key, 
				 // then it lies in right subtree 
				 else if (word > root->word)
					 root->rightChild = deleteNode(root->rightChild, word);

				 // if key is same as root's key, then This is the node 
				 // to be deleted 
				 else
				 {
					 // node with only one child or no child 
					 if (root->leftChild == NULL)
					 {
						 Node *temp = root->rightChild;
						 root = NULL;
						 return temp;
					 }
					 else if (root->rightChild == NULL)
					 {
						 Node *temp = root->leftChild;
						 root = NULL;
						 return temp;
					 }

					 // node with two children: Get the inorder successor (smallest 
					 // in the right subtree) 
					 Node* temp = minValueNode(root->rightChild);

					 // Copy the inorder successor's content to this node 
					 root->word = temp->word;

					 // Delete the inorder successor 
					 root->rightChild = deleteNode(root->rightChild, temp->word);
				 }
				 return root;
			 }

			  

			  void BFS(Node *root) {

		}







		
		void Inorder(Node *root,list<Node *> &wordList,string word) {

			if (root == NULL) {
				return;
			}
			
			Inorder(root->leftChild,wordList,word);
			
			if (root->word.rfind(word, 0) == 0) {
				wordList.push_back(root);
				// s starts with prefix
			}
			//cout<<root->word + " " ; 
			
			
			Inorder(root->rightChild, wordList, word);
			

		}
		void printInorder(struct Node* node)
		{
			if (node == NULL)
				return;

			/* first recur on left child */
			printInorder(node->leftChild);

			/* then print the data of node */
			cout << node->word << " ";

			/* now recur on right child */
			printInorder(node->rightChild);
		}

};


int main()
{
	
	
	BinarySearchTree bst;
	//bst.insert("a", "A");
	bst.insert("cat", "gata");
	bst.insert("air", "aeras");
	bst.insert("dub", "adexios");
	//bst.insert("b", "B");
	bst.insert("egg", "augo");
	bst.insert("dust", "skoni");
	bst.insert("dumb", "xazos");
//	bst.insert("g", "G");
	bst.insert("fun", "asteios");
	bst.insert("dunk", "karfwnw");
	//cout<<bst.search("f")->word;
	
	//bst.insert("b", "B");
	/*bst.insert("c", "C");
	bst.insert("e", "E");
	bst.insert("g", "G");
	bst.insert("f", "F");
	*/
	bst.printInorder(bst.root);
	//bst.search("du");
	//bst.printInorder(bst.root);
	cout << endl;
	bst.deleteNode(bst.root,"cat");
	bst.printInorder(bst.root);
	//bst.search("du");
	//bst.printInorder(bst.root);
    return 0;
}

