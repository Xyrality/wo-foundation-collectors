/**
 * Copyright (c) 2015, Xyrality GmbH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of wo-foundation-collectors nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * 
 */
package com.xyrality.utils;

import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;
import com.webobjects.foundation.NSSet;

/**
 * Implementation of helper {@link Collector}s to facilitate the use of streams in conjunction with Foundation classes.
 * These collectors can be used in a stream to collect the results into Foundation containers (like NSSet or NSMutableArray).
 *
 * Example: random.ints(100).collect(NSCollectors.toNSSet())
 */
public class NSCollectors<T>
{
	public static <T> Collector<T, ?, NSMutableSet<T>> toNSMutableSet()
	{
		return Collector.of(
				NSMutableSet<T>::new,
				NSMutableSet<T>::add,
				(NSMutableSet<T> left, NSMutableSet<T> right) -> {
					left.addAll(right);
					return left;
				},
				Characteristics.UNORDERED);
	}

	public static <T> Collector<T, ?, NSSet<T>> toNSSet()
	{
		return Collector.of(
				NSMutableSet<T>::new,
				NSMutableSet<T>::add,
				(NSMutableSet<T> left, NSMutableSet<T> right) -> {
					left.addAll(right);
					return left;
				},
				nsMutableSet -> nsMutableSet.immutableClone(),
				Characteristics.UNORDERED);
	}

	public static <T> Collector<T, ?, NSMutableArray<T>> toNSMutableArray()
	{
		return Collector.of(
				NSMutableArray<T>::new,
				NSMutableArray<T>::add,
				(NSMutableArray<T> left, NSMutableArray<T> right) -> {
					left.addAll(right);
					return left;
				});
	}

	public static <T> Collector<T, ?, NSArray<T>> toNSArray()
	{
		return Collector.<T, NSMutableArray<T>, NSArray<T>>of(
				NSMutableArray<T>::new,
				NSMutableArray<T>::add,
				(left, right) -> {
					left.addAll(right);
					return left;
				},
				nsMutableArray -> nsMutableArray.immutableClone());
	}
}
