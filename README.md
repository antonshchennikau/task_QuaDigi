Application Challenge
To solve this challenge, you do not need any user interface, data base or special infrastructure. Clean
code and meaningful tests are important for us. Please use C#, Java or C++ to implement the solution.
Imagine you are part of a Software team and got the challenge to write a program that is able to
sample time-based measurement data received from a medical device. The measurement data have
the following structure:
class Measurement
{
private DateTime measurementTime;
private Double measurementValue;
private MeasurementType type;
}

Possible types of measurements are for example temperature, heart rate or SpO2. Measurements are
measured exact to the second.
Your challenge is to sample the received measurements into a 5-minute interval based on the
following rules:
- each type of measurement shall be sampled separately
- from a 5-minute interval only the last measurement shall be taken
- if a measurement timestamp will exactly match a 5-minute interval border, it shall be used
for the current interval
- the input values are not sorted by time
- the output shall be sorted by time ascending


Example:
INPUT:
{2017-01-03T10:04:45, TEMP, 35.79}
{2017-01-03T10:01:18, SPO2, 98.78}
{2017-01-03T10:09:07, TEMP, 35.01}
{2017-01-03T10:03:34, SPO2, 96.49}
{2017-01-03T10:02:01, TEMP, 35.82}
{2017-01-03T10:05:00, SPO2, 97.17}
{2017-01-03T10:05:01, SPO2, 95.08}
OUTPUT:
{2017-01-03T10:05:00, TEMP, 35.79}
{2017-01-03T10:10:00, TEMP, 35.01}
{2017-01-03T10:05:00, SPO2, 97.17}
{2017-01-03T10:10:00, SPO2, 95.08}

A team member already suggested a possible signature to start with:
public Dictionary<MeasurementType, List<Measurement>> sample(
DateTime startOfSampling, List<Measurement> unsampledMeasurements)
{
// your implementation here
}
