package dna;
//NOTE no matter what I did the github checks would not work. But the program works on prairelearn and with my own tests



import java.util.HashSet;

public class DNA {
    String nucSeq;
    String ogNucSeq;
    boolean valid = true;
    HashSet<String> codons = new HashSet<String>();

    int Acount = 0;
    int Tcount = 0;
    int Ccount = 0;
    int Gcount = 0;
    int Jcount = 0;
    double Mass = 0;

    /**
     *
     * @param The unmodified DNA sequence s
     *        requires that s is not null, or an invalid DNA sequence
     * @throws If the sequence is not valid, IllegalArdumentException is thrown
     */

    //Default constructor "DNA", initialized with a string of unmodified nucleotides
    public DNA(String s) {
        this.ogNucSeq = s;
        checkCount(s);
        //checks validity
        if ((this.nucSeq.length() % 3) != 0) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }

    }

    /**
     *
     * @param Takes the unmodified String s
     *        Requires s to not be null, or invalid
     * @return Provides an integer count for each nucleotide & junk
     *         Removes junk, then assigns the sequence to the global variable nucSeq
     */
    public void checkCount(String s) {
        char cont;
        String exp;

        //Removes junk from the sequence, making subsequent method calls faster
        //Also counts the amount of each base
        for (int i = 0; i < s.length(); i++) {
            cont = s.charAt(i);
            switch (cont) {
                case 'A' -> {
                    this.Acount++;
                    System.out.println("A" + this.Jcount);
                }
                case 'T' -> {
                    this.Tcount++;
                    System.out.println("T" + this.Jcount);
                }
                case 'C' -> {
                    this.Ccount++;
                    System.out.println("C" + this.Jcount);
                }
                case 'G' -> {
                    this.Gcount++;
                    System.out.println("G" + this.Jcount);
                }
                default -> {
                    //anything else is junk
                    this.Jcount++;
                    System.out.println("J" + this.Jcount);
                    try {
                        s = s.replaceFirst(Character.toString(cont), "");
                    } catch (Exception e) {

                        StringBuilder d = new StringBuilder(s);
                        d.deleteCharAt(i);
                        s = d.toString();
                    }
                    i -= 1;
                    if (i < 0) {
                        i = -1;
                    }


                }


            }

        }
        this.nucSeq = s;
    }

    /**
     * @param Requires that the DNA sequence starts with the "ATG" codon
     *        Requires that the sequnce ends with the "TAA", "TAG", or "TAG" codons
     *        Requires the set of codons must be greater than 5
     *        Requres that C, and G bases make up at least 30% of the total mass
     * @return In the case of a failed requirment, false is returned (not protein)
     *
     */
    public boolean isProtein() {

        //Checks if the start codon is valid
        if (this.nucSeq.charAt(0) != 'A' || this.nucSeq.charAt(1) != 'T' || this.nucSeq.charAt(2) != 'G') {
            return false;
        }

        //Checks if the stop codon is valid
        for (int l = 1; l <= 3; l++) {

            if (l == 1 && this.nucSeq.charAt((this.nucSeq.length() - l)) != 'A' && this.nucSeq.charAt((this.nucSeq.length() - l)) != 'G') {

                return false;
            } else if (l == 2 && this.nucSeq.charAt((this.nucSeq.length() - l)) != 'A' && this.nucSeq.charAt((this.nucSeq.length() - l)) != 'G') {

                return false;
            } else if (l == 3 && this.nucSeq.charAt((this.nucSeq.length() - l)) != 'T') {

                return false;
            }
        }

        codonSet();
        //Make sure there are at least 5 codons in the sequence
        if (codons.size() < 5) {
            return false;
        }

        totalMass();
        //Checks if C & G make up 30% mass
        if ((this.Ccount * 111.103) + (this.Gcount * 151.128) < (0.3 * this.Mass)) {
            return false;
        }

        return true;
    }


    /**
     *
     * @return A HashSet of valid codons within the DNA sequence (not including junk)
     */
    public HashSet<String> codonSet() {

        for (int j = 0; j < this.nucSeq.length(); j += 3) {
            codons.add(this.nucSeq.substring(j, j + 3));
        }

        return codons;
    }

    /**
     *
     * @return Returns the original (including junk) dna sequence if an invalid mutation has occured
     *         Otherwise returns the modified (junk removed) dna sequence
     */
    public String sequence() {
        if (this.valid) {
            return this.nucSeq;
        } else {
            return this.ogNucSeq;
        }

    }

    /**
     *
     * @param ogcod - the codon in the dna sequence to be removed. Must be valid
     * @param newcod - the new codon intended to replace the old codon. Must be valid
     *
     * @frame Recounts the bases and junk, and the mass
     * @return
     *         A new dna sequence with the mutation
     */
    public String mutateCodon(String ogcod, String newcod) {
//Junk is already removed in the default constructor

        //First need to make sure that both codons are valid
        //To be valid: 3 chars, Only A,G,T,C are permitted
        if (checkValid(ogcod) && checkValid((newcod))) {
            this.nucSeq = this.nucSeq.replaceAll(ogcod, newcod);

            //Resets and updates the nucleotide count and total mass after the mutation
            this.Acount = 0;
            this.Ccount = 0;
            this.Gcount = 0;
            this.Tcount = 0;
            this.Jcount = 0;
            checkCount(this.nucSeq);

            totalMass();

        } else {
            this.valid = false;
            return this.ogNucSeq;
        }

        return this.nucSeq;
    }

    /**
     *
     * @param Takes a string of codons. Require there to be 3 characters in the codon
     *
     * @return Returns false if there are more than 3 characters or junk is contained in a codon of size 3
     */
    public boolean checkValid(String c) {

        if (c.length() > 3) {
            return false;
        }

        for (int j = 0; j < c.length(); j++) {
            switch (c.charAt(j)) {
                case 'A':
                case 'T':
                case 'C':
                case 'G':
                    break;
                default:
                    //anything else is not valid
                    return false;
            }
        }


        return true;
    }

    /**
     *
     * @return Computes the total mass using base/junk counts and molecular weights
     */
    public double totalMass() {
        this.Mass = (this.Acount * 135.128) + (this.Tcount * 125.107) + (this.Gcount * 151.128)
                + (this.Ccount * 111.103) + (this.Jcount * 100.000);

        return Math.round(this.Mass * 10.0) / 10.0;
    }

    /**
     *
     * @param takes a char l
     * @return returns the correct count for each base and 0 for junk
     */
    public int nucleotideCount(char l) {

        return switch (l) {
            case 'A' -> this.Acount;
            case 'C' -> this.Ccount;
            case 'G' -> this.Gcount;
            case 'T' -> this.Tcount;
            default -> 0;
        };
    }


}

