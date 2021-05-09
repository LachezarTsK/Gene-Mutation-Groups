package geneMutationGroups.gitHub;

public class Solution {

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(String [] genes)'
  so that the code can be run on the website. Even though the name 'solve'
  does not make a lot of sense, it is left as it is, so that the code can
  be run directly on the website, without any modifications.
  */
  public int solve(String[] genes) {
    return findNumberOfGeneGroups(genes);
  }

  public int findNumberOfGeneGroups(String[] genes) {

    UnionFind unionFind = new UnionFind(genes.length);
    int countGeneGroups = genes.length;

    for (int i = 0; i < genes.length; i++) {
      for (int j = i + 1; j < genes.length; j++) {

        if (belongToSameGroup(genes[i], genes[j])) {

          int geneOne = unionFind.findParent(i);
          int geneTwo = unionFind.findParent(j);

          if (geneOne != geneTwo) {
            unionFind.union(geneOne, geneTwo);
            countGeneGroups--;
          }
        }
      }
    }
    return countGeneGroups;
  }

  public boolean belongToSameGroup(String geneOne, String geneTwo) {

    int difference = 0;
    for (int i = 0; i < geneOne.length(); i++) {
      if (geneOne.charAt(i) != geneTwo.charAt(i)) {

        if (++difference > 1) {
          return false;
        }
      }
    }
    return true;
  }
}

class UnionFind {

  int numberOfGenes;
  Subset[] subsets;

  public UnionFind(int numberOfGenes) {

    this.numberOfGenes = numberOfGenes;
    this.subsets = new Subset[numberOfGenes];

    for (int i = 0; i < numberOfGenes; i++) {
      subsets[i] = new Subset();
      subsets[i].parent = i;
    }
  }

  public int findParent(int gene) {

    if (subsets[gene].parent != gene) {
      subsets[gene].parent = findParent(subsets[gene].parent);
    }
    return subsets[gene].parent;
  }

  public void union(int geneOne, int geneTwo) {

    if (subsets[geneOne].rank > subsets[geneTwo].rank) {
      subsets[geneTwo].parent = geneOne;
    } else if (subsets[geneOne].rank < subsets[geneTwo].rank) {
      subsets[geneOne].parent = geneTwo;
    } else {
      subsets[geneTwo].parent = geneOne;
      subsets[geneOne].rank++;
    }
  }
}

class Subset {

  int parent;
  int rank;
}
