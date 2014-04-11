package com.viadeo.axonframework.eventhandling.terminal.kafka;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TerminalMetricHelperUTest {

    private Meter meter;
    private MetricRegistry metricRegistry;

    @Before
    public void setUp() {
        meter = mock(Meter.class);

        metricRegistry = mock(MetricRegistry.class);
        when(metricRegistry.meter(anyString())).thenReturn(meter);
    }

    @Test
    public void markReceivedMessage_markTwoMeters() {
        // Given
        final String prefix = "foo";
        final TerminalMetricHelper terminalMetricHelper = new TerminalMetricHelper(metricRegistry, prefix);

        // When
        terminalMetricHelper.markReceivedMessage("bar");

        // Then
        verify(metricRegistry).meter("foo.bar.received_message");
        verify(metricRegistry).meter("foo.received_message");
        verify(meter, times(2)).mark();
    }

    @Test
    public void markErroredWhileReceivingMessage_markTwoMeters() {
        // Given
        final String prefix = "foo";
        final TerminalMetricHelper terminalMetricHelper = new TerminalMetricHelper(metricRegistry, prefix);

        // When
        terminalMetricHelper.markErroredWhileReceivingMessage("bar");

        // Then
        verify(metricRegistry).meter("foo.bar.received_message_errors");
        verify(metricRegistry).meter("foo.received_message_errors");
        verify(meter, times(2)).mark();
    }

    @Test
    public void markSentMessage_markTwoMeters() {
        // Given
        final String prefix = "foo";
        final TerminalMetricHelper terminalMetricHelper = new TerminalMetricHelper(metricRegistry, prefix);

        // When
        terminalMetricHelper.markSentMessage("bar");

        // Then
        verify(metricRegistry).meter("foo.bar.sent_message");
        verify(metricRegistry).meter("foo.sent_message");
        verify(meter, times(2)).mark();
    }

    @Test
    public void markErroredWhileSendingMessage_markTwoMeters() {
        // Given
        final String prefix = "foo";
        final TerminalMetricHelper terminalMetricHelper = new TerminalMetricHelper(metricRegistry, prefix);

        // When
        terminalMetricHelper.markErroredWhileSendingMessage("bar");

        // Then
        verify(metricRegistry).meter("foo.bar.sent_message_errors");
        verify(metricRegistry).meter("foo.sent_message_errors");
        verify(meter, times(2)).mark();
    }
}
