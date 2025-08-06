# User Activity Log Manager
## Overview
This Java-based User Activity Log Manager demonstrates robust implementation of custom data structures and algorithms for efficient log processing. The system provides command-line analytics for user activity logs, including frequency analysis and temporal filtering, with optimized data handling.

## Key Features
* Activity Frequency Analysis: Identify top user activities with custom frequency counting algorithm

* Temporal Filtering: Retrieve logs by specific date (MM/DD/YYYY) or hour (0-23)

* Data Validation: Comprehensive input validation for all user queries

* Optimized Storage: Efficient log storage using custom data structures

* Error Handling: Graceful error recovery and user feedback

# Technical Implementation

## Core Data Structures & Algorithms

<img width="1588" height="1314" alt="deepseek_mermaid_20250806_aaa55b" src="https://github.com/user-attachments/assets/1736dcc1-346b-4c9d-9116-333e04156e19" />


## 1. Custom Map Implementation:

* UnorderedLinkedMap for O(1) average case insertions

* Date/Time bucketing for efficient temporal queries

* Action-resource pairing for frequency analysis

## 2. Sorting Algorithms:

* MergeSort for timestamp-based sorting (O(n log n))

* Custom frequency-based sorting for activity reports

## 3. Memory Management:

* Optimized linked list implementations

* Lazy loading of log entries

* Efficient data partitioning

## Performance Highlights
* Date-based queries: O(1) retrieval + O(k log k) sorting (k = entries per date)

* Top activities: O(n) frequency counting + O(m log m) sorting (m = unique activities)

* Hour-based queries: O(1) access with pre-sorted hourly buckets

# Testing & Validation
The project includes comprehensive test coverage:

* Unit Tests: 100% coverage of core functionality

* Boundary Cases: Invalid dates, empty files, edge hours

* Performance Testing: Verified O(n log n) sorting efficiency

* Experimental Report: Included in repository analyzing real-world performance metrics

# Skills Demonstrated
* Data Structure Design: Custom map/list implementations

* Algorithm Optimization: Efficient sorting and searching

* Java Best Practices: Strong OOP design, encapsulation

* Performance Analysis: Experimental complexity verification

* Robust Error Handling: Graceful degradation on invalid inputs

# Getting Started
## Prerequisites
* Java 11+

* Maven

## Installation

> git clone https://github.com/BreVisc3/UserActivityLogManager.git 
>
> cd UserActivityLogManager 
>
> mvn clean install

## Usage

> java -jar target/user-activity-log-manager.jar
>
> Sample workflow:
> 1. Load log data file
> 2. View top 10 frequent activities
> 3. Retrieve logs for 05/15/2023
> 4. Analyze logs from hour 14 (2PM)
