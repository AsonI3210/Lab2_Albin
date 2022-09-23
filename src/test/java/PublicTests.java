//package test;
//NOTE no matter what I did the github checks would not work. But the program works on prairelearn and with my own tests

import dna.DNA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    @Test
    @DisplayName("Create a DNA object and get its mass")
    public void test1_create_getMass() {
        DNA dna1 = new DNA("ATGCCAxCTATGGTAG");
       assertEquals("ATGCCACTATGGTAG", dna1.sequence());
        assertEquals(2078.8, dna1.totalMass(), 0.001);
    }

    @Test
    @DisplayName("Create a DNA object and get its mass")
    public void Mytest1_create_getMass1() {
        DNA dna1 = new DNA("ATGCCC");
        assertEquals(744.7, dna1.totalMass(), 0.001);
    }

    @Test
    @DisplayName("Create a DNA object and check if it is a protein")
    public void test2_create_checkProtein() {
        DNA dna2 = new DNA("ATGCCAACATGGATGCCCGATAT++GGATTG+A!");
        System.out.println("FINAL" + dna2.nucleotideCount('l'));
        assertTrue(dna2.isProtein());
    }

    @Test
    @DisplayName("Invalid sequence")
    public void test3_create_invalidSeq() {
        assertThrows(IllegalArgumentException.class, () -> new DNA("-TA"));
    }

    @Test
    @DisplayName("Test nucleotide count")
    public void test4_nucleotideCount() {
        DNA dna4 = new DNA("AAAGGTTACTGA");
        assertEquals(5, dna4.nucleotideCount('A'));
    }
    @Test
    @DisplayName("Test nucleotide count")
    public void MyTest4_nucleotideCount() {
        DNA dna4 = new DNA("AAAGGTTACCCCTGA");
        assertEquals(4, dna4.nucleotideCount('C'));
    }

    @Test
    @DisplayName("Test codon set")
    public void test5_codonSet() {
        DNA dna5 = new DNA("AAAGGTTACTGA");
        HashSet<String> expectedSet = new HashSet<>();
        expectedSet.add("AAA");
        expectedSet.add("GGT");
        expectedSet.add("TAC");
        expectedSet.add("TGA");
        assertEquals(expectedSet, dna5.codonSet());
    }


    @Test
    @DisplayName("Test getSequence")
    public void test6_getSequence() {
        DNA dna6 = new DNA("AAAGGTTACTGA");
        assertEquals("AAAGGTTACTGA", dna6.sequence());
    }

    @Test
    @DisplayName("Test mutation")
    public void test7_mutate() {
        DNA dna7 = new DNA("AAAGGTTACTG+A");
        dna7.mutateCodon("TGA", "GAT");
        assertEquals("AAAGGTTACGAT", dna7.sequence());
    }



    @Test
    @DisplayName("Test invalid mutation")
    public void test8_mutate() {
        DNA dna7 = new DNA("AAAGGTTACTG+A");
        dna7.mutateCodon("TGA", "G+T");
        assertEquals("AAAGGTTACTG+A", dna7.sequence());
    }


    @Test
    @DisplayName("Test mutation with junk removal")
    public void test9_mutate() {
        DNA dna9 = new DNA("ATGCCAxCTATGGTAG");
        dna9.mutateCodon("CTA", "ATC");

        System.out.println(dna9.nucleotideCount('o'));
        System.out.println(dna9.nucleotideCount('A'));
        System.out.println(dna9.nucleotideCount('G'));
        System.out.println(dna9.nucleotideCount('T'));
        System.out.println(dna9.nucleotideCount('C'));

        assertEquals(1978.8, dna9.totalMass(), 0.001);
        assertEquals("ATGCCAATCTGGTAG", dna9.sequence());
    }




    @Test
    public void myTest1() {
        DNA dna1 = new DNA("ATGT3GCAT+G");
        assertEquals("ATGTGCATG", dna1.sequence());
    }
}