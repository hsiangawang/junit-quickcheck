/*
 The MIT License

 Copyright (c) 2010-2020 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.pholser.junit.quickcheck;

import static com.google.common.collect.Sets.newHashSet;
import static com.pholser.junit.quickcheck.Mode.EXHAUSTIVE;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.experimental.results.PrintableResult.testResult;
import static org.junit.experimental.results.ResultMatchers.hasSingleFailureContaining;
import static org.junit.experimental.results.ResultMatchers.isSuccessful;


import com.pholser.junit.quickcheck.conversion.StringConversion;
import com.pholser.junit.quickcheck.generator.Only;
import com.pholser.junit.quickcheck.internal.ReflectionException;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;

public class ExhaustingAGivenSetTest {
    @Test public void primitiveBooleans() {
        assertThat(testResult(PrimitiveBooleans.class), isSuccessful());
        assertEquals(1, PrimitiveBooleans.iterations);
        assertEquals(singleton(true), PrimitiveBooleans.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveBooleans {
        static int iterations;

        private static final Set<Boolean> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only("true") boolean b) {
            testCases.add(b);
            ++iterations;
        }
    }

    @Test public void wrapperBooleans() {
        assertThat(testResult(WrapperBooleans.class), isSuccessful());
        assertEquals(1, WrapperBooleans.iterations);
        assertEquals(singleton(false), WrapperBooleans.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperBooleans {
        static int iterations;

        private static final Set<Boolean> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only("false") boolean b) {
            testCases.add(b);
            ++iterations;
        }
    }

    @Test public void booleansUnmarked() {
        assertThat(testResult(UnmarkedBooleans.class), isSuccessful());
        assertEquals(2, UnmarkedBooleans.iterations);
        assertEquals(new HashSet<>(asList(true, false)), UnmarkedBooleans.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class UnmarkedBooleans {
        static int iterations;

        private static final Set<Boolean> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(boolean b) {
            testCases.add(b);
            ++iterations;
        }
    }

    @Test public void primitiveBytes() {
        assertThat(testResult(PrimitiveBytes.class), isSuccessful());
        assertEquals(2, PrimitiveBytes.iterations);
        assertEquals(
            new HashSet<>(asList(Byte.valueOf("12"), Byte.valueOf("-13"))),
            PrimitiveBytes.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveBytes {
        static int iterations;

        private static final Set<Byte> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"12", "-13"}) byte b) {
            testCases.add(b);
            ++iterations;
        }
    }

    @Test public void wrapperBytes() {
        assertThat(testResult(WrapperBytes.class), isSuccessful());
        assertEquals(2, WrapperBytes.iterations);
        assertEquals(
            new HashSet<>(asList(Byte.valueOf("14"), Byte.valueOf("-15"))),
            WrapperBytes.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperBytes {
        static int iterations;

        private static final Set<Byte> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"14", "-15"}) Byte b) {
            testCases.add(b);
            ++iterations;
        }
    }

    @Test public void primitiveChars() {
        assertThat(testResult(PrimitiveChars.class), isSuccessful());
        assertEquals(2, PrimitiveChars.iterations);
        assertEquals(
            new HashSet<>(asList('Z', 'z')),
            PrimitiveChars.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveChars {
        static int iterations;

        private static final Set<Character> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"Z", "z"}) char ch) {
            testCases.add(ch);
            ++iterations;
        }
    }

    @Test public void wrapperChars() {
        assertThat(testResult(WrapperChars.class), isSuccessful());
        assertEquals(2, WrapperChars.iterations);
        assertEquals(
            new HashSet<>(asList('@', '#')),
            WrapperChars.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperChars {
        static int iterations;

        private static final Set<Character> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"@", "#"}) Character ch) {
            testCases.add(ch);
            ++iterations;
        }
    }

    @Test public void primitiveDoubles() {
        assertThat(testResult(PrimitiveDoubles.class), isSuccessful());
        assertEquals(2, PrimitiveDoubles.iterations);
        assertEquals(
            new HashSet<>(asList(3.2, -4D)),
            PrimitiveDoubles.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveDoubles {
        static int iterations;

        private static final Set<Double> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"3.2", "-4"}) double d) {
            testCases.add(d);
            ++iterations;
        }
    }

    @Test public void wrapperDoubles() {
        assertThat(testResult(WrapperDoubles.class), isSuccessful());
        assertEquals(2, WrapperDoubles.iterations);
        assertEquals(
            new HashSet<>(asList(2.7, -3.14)),
            WrapperDoubles.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperDoubles {
        static int iterations;

        private static final Set<Double> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE) public void shouldHold(@Only({"2.7", "-3.14"}) Double d) {
            testCases.add(d);
            ++iterations;
        }
    }

    @Test public void primitiveFloats() {
        assertThat(testResult(PrimitiveFloats.class), isSuccessful());
        assertEquals(2, PrimitiveFloats.iterations);
        assertEquals(
            new HashSet<>(asList(3.3F, -5F)),
            PrimitiveFloats.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveFloats {
        static int iterations;

        private static final Set<Float> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"3.3", "-5"}) float f) {
            testCases.add(f);
            ++iterations;
        }
    }

    @Test public void wrapperFloats() {
        assertThat(testResult(WrapperFloats.class), isSuccessful());
        assertEquals(2, WrapperFloats.iterations);
        assertEquals(
            new HashSet<>(asList(1.7F, -4.14F)),
            WrapperFloats.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperFloats {
        static int iterations;

        private static final Set<Float> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"1.7", "-4.14"}) Float f) {
            testCases.add(f);
            ++iterations;
        }
    }

    @Test public void primitiveIntegers() {
        assertThat(testResult(PrimitiveIntegers.class), isSuccessful());
        assertEquals(3, PrimitiveIntegers.iterations);
        assertEquals(
            new HashSet<>(asList(1, 2, 3)),
            PrimitiveIntegers.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveIntegers {
        static int iterations;

        private static final Set<Integer> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"1", "2", "3"}) int i) {
            testCases.add(i);
            ++iterations;
        }
    }

    @Test public void wrapperIntegers() {
        assertThat(testResult(WrapperIntegers.class), isSuccessful());
        assertEquals(2, WrapperIntegers.iterations);
        assertEquals(
            new HashSet<>(asList(4, 5)),
            WrapperIntegers.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperIntegers {
        static int iterations;

        private static final Set<Integer> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"4", "5"}) Integer i) {
            testCases.add(i);
            ++iterations;
        }
    }

    @Test public void primitiveLongs() {
        assertThat(testResult(PrimitiveLongs.class), isSuccessful());
        assertEquals(3, PrimitiveLongs.iterations);
        assertEquals(
            new HashSet<>(asList(-6L, -7L, -8L)),
            PrimitiveLongs.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveLongs {
        static int iterations;

        private static final Set<Long> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"-6", "-7", "-8"}) long ell) {
            testCases.add(ell);
            ++iterations;
        }
    }

    @Test public void wrapperLongs() {
        assertThat(testResult(WrapperLongs.class), isSuccessful());
        assertEquals(3, WrapperLongs.iterations);
        assertEquals(
            new HashSet<>(asList(10L, 11L, 12L)),
            WrapperLongs.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperLongs {
        static int iterations;

        private static final Set<Long> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"10", "11", "12"}) Long ell) {
            testCases.add(ell);
            ++iterations;
        }
    }

    @Test public void primitiveShorts() {
        assertThat(testResult(PrimitiveShorts.class), isSuccessful());
        assertEquals(2, PrimitiveShorts.iterations);
        assertEquals(
            new HashSet<>(asList(Short.valueOf("9"), Short.valueOf("8"))),
            PrimitiveShorts.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class PrimitiveShorts {
        static int iterations;

        private static final Set<Short> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"9", "8"}) short sh) {
            testCases.add(sh);
            ++iterations;
        }
    }

    @Test public void wrapperShorts() {
        assertThat(testResult(WrapperShorts.class), isSuccessful());
        assertEquals(2, WrapperShorts.iterations);
        assertEquals(
            new HashSet<>(asList(Short.valueOf("-13"), Short.valueOf("-14"))),
            WrapperShorts.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class WrapperShorts {
        static int iterations;

        private static final Set<Short> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"-13", "-14"}) Short sh) {
            testCases.add(sh);
            ++iterations;
        }
    }

    @Test public void strings() {
        assertThat(testResult(Strings.class), isSuccessful());
        assertEquals(2, Strings.iterations);
        assertEquals(
            new HashSet<>(asList("some", "values")),
            Strings.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class Strings {
        static int iterations;

        private static final Set<String> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"some", "values"}) String s) {
            testCases.add(s);
            ++iterations;
        }
    }

    @Test public void enums() {
        assertThat(testResult(Enums.class), isSuccessful());
        assertEquals(2, Enums.iterations);
        assertEquals(EnumSet.of(HALF_UP, HALF_EVEN), Enums.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class Enums {
        static int iterations;

        private static final Set<RoundingMode> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"HALF_EVEN", "HALF_UP"}) RoundingMode mode) {
            testCases.add(mode);
            ++iterations;
        }
    }

    @Test public void enumsUnmarked() {
        assertThat(testResult(EnumsUnmarked.class), isSuccessful());
        assertEquals(EnumSet.allOf(RoundingMode.class).size(), EnumsUnmarked.iterations);
        assertEquals(EnumSet.allOf(RoundingMode.class), EnumsUnmarked.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class EnumsUnmarked {
        static int iterations;

        private static final Set<RoundingMode> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(RoundingMode mode) {
            testCases.add(mode);
            ++iterations;
        }
    }

    @Test public void ctorOnly() {
        assertThat(testResult(CtorOnly.class), isSuccessful());
        assertEquals(2, CtorOnly.iterations);
        assertEquals(
            new HashSet<>(
                asList(new CtorOnly.Target("a"), new CtorOnly.Target("b"))
            ),
            CtorOnly.testCases);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class CtorOnly {
        public static final class Target {
            private final String s;

            public Target(String s) {
                this.s = s;
            }

            @Override public int hashCode() {
                return Objects.hashCode(s);
            }

            @Override public boolean equals(Object o) {
                return o instanceof Target && Objects.equals(s, ((Target) o).s);
            }
        }

        static int iterations;

        private static final Set<Target> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"a", "b"}) Target t) {
            testCases.add(t);
            ++iterations;
        }
    }

    @Test public void favorValueOf() {
        assertThat(testResult(FavorValueOf.class), isSuccessful());
        assertEquals(2, FavorValueOf.iterations);
        assertEquals(
            new HashSet<>(
                asList(
                    FavorValueOf.Target.valueOf("a"),
                    FavorValueOf.Target.valueOf("b"))
            ),
            FavorValueOf.testCases
        );
    }

    @RunWith(JUnitQuickcheck.class)
    public static class FavorValueOf {
        public static final class Target {
            private String s;

            private Target() {
                // empty on purpose
            }

            public Target(String s) {
                throw new UnsupportedOperationException(s);
            }

            @Override public int hashCode() {
                return Objects.hashCode(s);
            }

            @Override public boolean equals(Object o) {
                return o instanceof Target && Objects.equals(s, ((Target) o).s);
            }

            public static Target valueOf(String s) {
                Target t = new Target();
                t.s = s;
                return t;
            }
        }

        static int iterations;

        private static final Set<Target> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"a", "b"}) Target t) {
            testCases.add(t);
            ++iterations;
        }
    }

    @Test public void noImplicitConversion() {
        assertThat(
            testResult(NoImplicitConversion.class),
            hasSingleFailureContaining(ReflectionException.class.getName()));
        assertEquals(0, NoImplicitConversion.iterations);
    }

    @RunWith(JUnitQuickcheck.class)
    public static class NoImplicitConversion {
        public static final class Target {
        }

        static int iterations;

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(@Only({"a", "b"}) Target t) {
            ++iterations;
        }
    }

    @Test public void explicitConversion() {
        assertThat(testResult(ExplicitConversion.class), isSuccessful());
        assertEquals(2, ExplicitConversion.iterations);
        assertEquals(
            new HashSet<>(asList(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2001, 12, 25))),
            ExplicitConversion.testCases
        );
    }

    @RunWith(JUnitQuickcheck.class)
    public static class ExplicitConversion {
        public static final class YYYYMMDD implements StringConversion {
            private final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy/MM/dd");

            @Override public LocalDate convert(String raw) {
                return LocalDate.parse(raw, formatter);
            }
        }

        static int iterations;

        private static final Set<LocalDate> testCases = new HashSet<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(
            @Only(value = {"2017/01/01", "2001/12/25"}, by = YYYYMMDD.class)
            LocalDate d) {

            testCases.add(d);
            ++iterations;
        }
    }

    @Test public void manyParameters() {
        ArrayList<ManyParameters.pair<Integer, Character>> exhaustiveList = new ArrayList<>();

        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-1, 'r'));
        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-2, 'r'));
        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-4, 'r'));
        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-1, 'y'));
        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-2, 'y'));
        exhaustiveList.add(new ManyParameters.pair<Integer, Character>(-4, 'y'));
        
        assertThat(testResult(ManyParameters.class), isSuccessful());
        assertEquals(6, ManyParameters.iterations);
        /*assertEquals(
            asList(-1, -2, -4, -1, -2, -4),
            ManyParameters.firstTestCases
        );
        assertEquals(
            asList('r', 'r', 'r', 'y', 'y', 'y'),
            ManyParameters.secondTestCases
        );*/
        assertTrue(exhaustiveList.size() == ManyParameters.exhaustiveList.size() && exhaustiveList.containsAll(ManyParameters.exhaustiveList) && ManyParameters.exhaustiveList.containsAll(exhaustiveList));
    }

    @RunWith(JUnitQuickcheck.class)
    public static class ManyParameters {
        static int iterations;

        private static final List<Integer> firstTestCases = new LinkedList<>();
        private static final List<Character> secondTestCases = new LinkedList<>();
        private static final ArrayList<pair<Integer, Character>> exhaustiveList = new ArrayList<>();

        public static class pair<first,second>{
            public first First;
            public second Second;
            private pair(first First,second Second){
                this.First = First;
                this.Second = Second;
            }
            @Override
            public boolean equals(Object o) { 
 
                if (o == this) { 
                    return true; 
                } 

                if (!(o instanceof pair)) { 
                    return false; 
                } 
                pair c = (pair) o; 
                return First == c.First && Second == c.Second; 
            } 
        }

        @Property(mode = EXHAUSTIVE) public void shouldHold(
            @Only({"-1", "-2", "-4"}) int i,
            @Only({"r", "y"}) char ch) {

            firstTestCases.add(i);
            secondTestCases.add(ch);
            exhaustiveList.add(new ManyParameters.pair<Integer, Character>(i, ch));
            ++iterations;
        }
    }

    @Test public void manyParametersWithBooleanAndEnum() {
        assertThat(testResult(ManyParametersWithBooleanAndEnum.class), isSuccessful());

        assertEquals(
            2 * 5 * 2 * RoundingMode.values().length,
            ManyParametersWithBooleanAndEnum.iterations);
        assertEquals(
            newHashSet(3, 7),
            new HashSet<>(ManyParametersWithBooleanAndEnum.firstTestCases));
        assertEquals(
            newHashSet('a', 'b', 'c', 'd', 'e'),
            new HashSet<>(ManyParametersWithBooleanAndEnum.secondTestCases));
        assertEquals(
            newHashSet(false, true),
            new HashSet<>(ManyParametersWithBooleanAndEnum.thirdTestCases));
        assertEquals(
            EnumSet.allOf(RoundingMode.class),
            new HashSet<>(ManyParametersWithBooleanAndEnum.fourthTestCases));
    }

    @RunWith(JUnitQuickcheck.class)
    public static class ManyParametersWithBooleanAndEnum {
        static int iterations;

        private static final List<Integer> firstTestCases = new LinkedList<>();
        private static final List<Character> secondTestCases = new LinkedList<>();
        private static final List<Boolean> thirdTestCases = new LinkedList<>();
        private static final List<RoundingMode> fourthTestCases = new LinkedList<>();

        @Property(mode = EXHAUSTIVE)
        public void shouldHold(
            @Only({"3", "7"}) int i,
            @Only({"a", "b", "c", "d", "e"}) char ch,
            boolean b,
            RoundingMode mode) {

            firstTestCases.add(i);
            secondTestCases.add(ch);
            thirdTestCases.add(b);
            fourthTestCases.add(mode);
            ++iterations;
        }
    }
}
